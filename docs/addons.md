# The binary-addon build mechanism

## Where it came from

`classes/kodi-common.bbclass` and `classes/kodi-addon.bbclass` were ported from
the `origin/master` branch of the same koenkooi/meta-kodi fork this whole layer
descends from -- but that branch is a genuinely different, older addon-packaging
generation (targeting Kodi "Nexus"/20-era), not something that was ever built
against the current Kodi 22 "Piers" recipe. Porting it required updating
`KODICODENAME` from `"Nexus"` to `"Piers"` in `kodi-common.bbclass`, and
verifying (see `architecture.md`'s addon-dev SDK section) that the CMake paths
it assumes are still real for kodi 22 before trusting any of it.

## What `kodi-addon.bbclass` actually does

A thin wrapper: `inherit cmake pkgconfig gettext`, then points the addon's own
`CMakeLists.txt` at kodi's exported addon-dev SDK (`CMAKE_MODULE_PATH`,
`KODI_INCLUDE_DIR`, see `architecture.md`), sets `ADDONS_TO_BUILD=${KODIADDONNAME}`
(kodi's own addon build system can build multiple addons from one source tree;
this restricts it to just the one), and packages a manual-install zip alongside
the normal ipk. `DEPENDS:append = " kodi-platform p8platform zip-native"` --
every binary addon needs the small `kodi-platform` helper library and
Pulse-Eight's `p8-platform` (already available in meta-openembedded/meta-oe,
no work needed there).

Any new binary Kodi addon recipe just needs:
```
inherit kodi-addon
KODIADDONNAME = "addon.actual.id"
```
plus the normal `SRC_URI`/`SRCREV`/`LICENSE` fields -- see
`recipes-multimedia/kodi-addons/kodi-addon-pvr-hts.bb` for the concrete example.

## kodi-platform: the one real bug found porting it

`recipes-multimedia/kodi/kodi-platform_git.bb` is a small C++ helper library
addons and libcec link against. The old fork's own inherited patch
(`kodi-platform-01_crosscompile-badness.patch`, from the same Nexus-era branch)
turned out to be **actively wrong** when reapplied blind against the current
upstream: it changed `kodiplatform_INCLUDE_DIRS` to an already-resolved
absolute sysroot path, but that variable is *never used for the local compile
at all* (the actual `include_directories()` call uses a separate
`KODI_INCLUDE_DIR` variable) -- it only feeds the **exported**
`kodiplatform-config.cmake`, where an already-resolved absolute path is exactly
the bug `do_package_qa`'s buildpaths check exists to catch. The patch was
dropped entirely rather than rebased.

The real, separate bug that *did* need fixing: `find_package(TinyXML)`'s
results (used by kodi-platform itself) get baked into that same exported config
file pre-resolved, with no deferred-path handling -- unlike the file's *own*
`CMAKE_INSTALL_PREFIX`-derived paths, which already defer correctly via a
literal, unexpanded `${CMAKE_FIND_ROOT_PATH}`. Fixed in
`kodi-platform-01_defer-tinyxml-sysroot-path.patch` by stripping the matching
`CMAKE_FIND_ROOT_PATH` entry into separate `*_EXPORT` variables, used only by
the exported config -- the real `TINYXML_INCLUDE_DIR`/`TINYXML_LIBRARIES`
values are untouched for kodi-platform's own compile/link. See
`architecture.md`'s note on this class of bug generally.

## pvr.hts (Tvheadend HTSP client)

`recipes-multimedia/kodi-addons/kodi-addon-pvr-hts.bb`, pinned to the
`22.9.1-Piers` tag. Upstream tags `pvr.hts` releases per Kodi codename (a full
`-Piers` series exists, `22.0.0` through at least `22.9.1`) -- always prefer
the newest matching `-<current-codename>` tag over reusing
whatever commit an old fork happened to pin, which will usually predate the
current Kodi codename entirely.

Needs a real Tvheadend *server* to actually connect to --
`meta-openembedded/meta-multimedia/recipes-dvb/tvheadend/tvheadend_git.bb`
(see its own git log for current status) is the matching server-side recipe,
not built by default into any image here.

## libcec: already solved, no porting needed

`meta-openembedded/meta-oe/recipes-extended/libcec/libcec_7.1.1.bb` already
exists and is already a confirmed `kodi` `RDEPENDS` -- HDMI-CEC support builds
and links today. The *old* meta-kodi fork's own `libcec_git.bb` (found on
several older branches, e.g. `origin/sumo`) is now entirely superseded and
should not be revived. What's genuinely unverified is CEC *usage* on real
hardware (does a connected TV remote actually control Kodi) -- that needs a
real board, not a recipe change.

## Addon activation

Kodi addons -- pvr.hts included -- **install disabled** by default. This
layer now turns pvr.hts on at boot; see "Implemented" below.

Kodi's own `CAddonMgr`/`CAddonDatabase` (`xbmc/addons/AddonManager.cpp`,
`AddonDatabase.cpp` -- stock Kodi behavior, not a LibreELEC patch) reads
`special://xbmc/system/addon-manifest.xml` at every startup. Any addon whose
ID is listed there gets enabled automatically the moment `FindAddons()`
discovers its `addon.xml` on disk -- **regardless of extension point type**
(`xbmc.service`, `xbmc.pvrclient`, anything) -- as a `system` entry
(force-`enabled=1` on every boot; a missing/unconfigured `system` addon is a
hard boot failure) or an `optional` one (auto-`enabled=1` the first time it's
discovered, not force-re-enabled afterward). LibreELEC uses exactly this for
its own always-on settings addon, `service.libreelec.settings`, injecting its
ID into the manifest via three `xmlstarlet ed` calls in
`packages/mediacenter/kodi/package.mk`'s own `post_makeinstall_target()` --
baked into the image at kodi-build time, no first-boot script involved. See
`libreelec-integration-menu.md` section B for the fuller LibreELEC-side
writeup.

This project is already halfway onto this exact mechanism: `kodi-addon.bbclass`
already installs a binary addon's files straight into
`${datadir}/kodi/addons/<id>` (see `FILES:${PN} += "${KODIADDONDIR}"` in the
class) -- exactly the "files present on disk before first boot" half this
mechanism needs. LibreELEC's zip-repository path (`PKG_IS_ADDON="yes"`, what
`pvr.hts` actually uses *there*) is the one that stays dormant -- this
project was never on that path to begin with. `special://xbmc/system/addon-manifest.xml`
is confirmed as a plain source file in Kodi's own tree, not generated (xbmc's
top-level CMakeLists.txt feeds `cmake/installdata/common/common.txt` -- which
lists it -- through `install_data`, installed by
`cmake/scripts/linux/Install.cmake`'s `install(FILES ...)` loop straight to
`${datarootdir}/kodi/system/`), so it lands in `${D}` at kodi's own
`do_install` like any other installed file.

**Implemented, at the addon package, not the base kodi recipe.** A
`pkg_postinst:${PN}()` on `kodi-addon-pvr-hts.bb` seds
`<addon optional="true">pvr.hts</addon>` into `addon-manifest.xml` before the
closing `</addons>` tag (`optional`, not `system` -- a missing/unconfigured
`system` entry hard-fails Kodi's boot, `optional` doesn't), guarded by an
idempotent `grep` against double-insertion on rebuild/reinstall.
`RDEPENDS:${PN} += "kodi"` guarantees install ordering so kodi's manifest is
already in the rootfs; `$D` covers both the rootfs-time execution path
(preferred -- runs during `do_rootfs` via pseudo) and the deferred
first-boot path a `read-only-rootfs` image would force instead. Keeping this
on the addon recipe rather than `kodi_21+22.0-beta3.bb` means adding a second
auto-activated addon later means writing its own postinst, not editing
kodi's recipe again.

Fallbacks, no longer needed but kept for reference:

- A first-boot systemd oneshot calling `Addons.SetAddonEnabled` over Kodi's
  JSON-RPC socket (needs `jsonrpc` enabled via a seeded
  `advancedsettings.xml`) -- superseded by the manifest edit above.
- Ship a pre-populated `userdata/Database/Addons33.db` as a committed binary
  seed -- still discouraged, rots silently against the next Kodi DB schema
  bump.

Not yet verified: an actual boot test that pvr.hts shows up enabled in the
Kodi UI/JSON-RPC (this was a recipe-only change, no image build done here).

Not independently verified: which Kodi-internal component actually launches
the `xbmc.service` Python thread once `CAddonMgr` marks an addon enabled
(well-established Kodi behavior, but not traced to a specific source line
here) -- worth confirming before leaning on it for anything more elaborate
than "gets enabled."

See `libreelec-integration-menu.md` section B for the fuller writeup,
including the local-addon-repository idea as the more structural fix.
