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

## The open gap: addon activation

Kodi addons -- pvr.hts included -- **install disabled**. Nothing in this layer
currently turns one on. Confirmed via a structured comparison against
LibreELEC (see `libreelec-integration-menu.md`, section B) that there's no
prior art to port here either -- LibreELEC's own answer is architectural (ship
addons via a local Kodi-repository feed, so *installing* one enables it in the
same step, rather than pre-seeding an "enabled" flag anywhere), not a
first-boot trick. Two concrete options remain open, neither implemented yet:

1. A first-boot systemd oneshot that waits for Kodi's JSON-RPC socket (needs
   `jsonrpc` enabled via a seeded `advancedsettings.xml`) and calls
   `Addons.SetAddonEnabled`.
2. Ship a pre-populated `userdata/Database/Addons33.db` as a committed binary
   seed -- discouraged, since it silently rots against the next Kodi DB schema
   bump; prefer (1).

See `libreelec-integration-menu.md` section B for the fuller writeup,
including the local-addon-repository idea as the more structural fix.
