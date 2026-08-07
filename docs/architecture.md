# How the kodi build actually fits together

## The three kodi-source.inc recipes

`recipes-multimedia/kodi/kodi-source.inc` holds the shared `PV`/`SRCREV`/`SRC_URI`
for three separate recipes that all build from the same Kodi source tree:

- `kodi_21+22.0-beta3.bb` -- the media center itself (`inherit cmake`, the real build)
- `kodi-jsonschemabuilder-native_21+22.0-beta3.bb` -- a native build tool, built
  from a subdirectory of the same tree, needed by kodi's own build for JSON
  schema codegen
- `kodi-texturepacker-native_21+22.0-beta3.bb` -- same idea, for texture atlas
  packing

All three point `SRCREV` at the same commit (currently the `22.0b1-Piers` tag,
`77395cf42e...`, published 2026-06-21 -- re-check upstream tags before
assuming this is still current).

## The PV/PE versioning gotcha (a real incident, not a warning)

`kodi-source.inc` uses `PV = "21+22.0-beta3"` -- the `"<last-stable>+<target>-<tag>"`
scheme, deliberately chosen because opkg treats a bare `"22.0-beta3"` as sorting
*above* the eventual final `"22.0"`, which would look like a downgrade once that
ships. This PV scheme itself is correct and should not be "fixed" back to
`22.0~beta1` or similar.

**What it doesn't automatically protect against:** if a previous build already
shipped a *different* PV scheme for the same recipe (this project's own history:
an earlier local build shipped `PV = "22.0~beta1"`, using the `~` pre-release
operator), the new scheme can still sort *below* the old one as a plain string
comparison. That's exactly what happened here -- `do_packagedata`'s
version-going-backwards QA check hard-failed the build the first time this PV
scheme was actually build-tested against buildhistory that remembered the old one.

**The fix is `PE` (epoch), not touching `PV`.** `kodi-source.inc` carries
`PE = "1"` specifically for this. If you ever see a version-going-backwards QA
failure on a kodi-family recipe, the PV scheme comment above already explains
why the string looks the way it does -- don't reflexively "fix" the string
itself; bump PE (or confirm the existing one is high enough) instead.

## Windowing: Wayland + Weston, not GBM

`kodi-source.inc`'s `CORE_PLATFORM_NAME` is derived from whichever of
`gbm wayland x11` appear in `PACKAGECONFIG` (semicolon-joined, since Kodi's own
`Platform.cmake` wants that, not space-separated -- see the comment in the
recipe for why this needs backslash-escaping through `EXTRA_OECMAKE`). This
project runs the **wayland** backend, with Kodi launched under a dedicated
**Weston** kiosk session (`kodi-weston_1.0.bb`, `kodi-weston/weston-kodi.ini`),
not the classic direct-tty1/gbm `kodi.service` path that ships in the kodi
recipe itself.

`kodi-image.bb` (in the sibling `meta-angstrom` layer,
`recipes-images/angstrom/kodi-image.bb`) is what actually wires this up:
`REQUIRED_DISTRO_FEATURES = "opengl systemd wayland"`, installs
`kodi kodi-weston weston weston-init wayland-utils mesa-megadriver`, sets
`SYSTEMD_DEFAULT_TARGET = "graphical.target"`, and explicitly disables the
tty1-direct `kodi.service` in favor of Weston's autolaunch
(`kodi_image_disable_tty_kodi()`).

**The direct-tty1/gbm `kodi.service` path is real but currently unverified and
likely broken for actual hardware use.** It ships in the kodi recipe (see
`recipes-multimedia/kodi/kodi/kodi.service`) but `kodi-image.bb` deliberately
disables it. It has `User=kodi` + `TTYPath=/dev/tty1` with **no `PAMName=`** and
no `seat` in its supplementary groups -- without a PAM stack there's no logind
session, so the gbm backend can't acquire DRM master. This can't be validated
under qemu's Weston-based image (a different code path entirely); it only
matters if a future machine wants gbm-direct instead of Weston, and would need
real-hardware testing to fix properly, not a guess.

## The addon-dev SDK kodi exports (why kodi-addon.bbclass works)

Kodi's own upstream CMake build exports a full addon-development SDK as part of
its normal install -- this isn't an OE-specific thing, it's how Kodi lets
third-party binary addons (PVR clients, visualizations, game cores, etc.) build
against an installed Kodi without vendoring Kodi's own source tree. Confirmed
via this project's own `kodi-dev` package contents:

- `${includedir}/kodi/...` -- the full addon C++ API headers (`AddonBase.h`,
  `gui/`, `tools/`, etc.)
- `${libdir}/kodi/KodiConfig.cmake` -- the CMake package-config entry point
- `${datadir}/kodi/cmake/AddonHelpers.cmake` and friends -- the actual macros
  (`build_addon()` etc.) a binary addon's own `CMakeLists.txt` calls

`classes/kodi-addon.bbclass` (ported from an older koenkooi/meta-kodi
generation, see `addons.md`) points `CMAKE_MODULE_PATH` at exactly
`${STAGING_DIR_HOST}${libdir}/kodi;${STAGING_DIR_HOST}${datadir}/kodi/cmake` and
`KODI_INCLUDE_DIR` at `${STAGING_INCDIR}/kodi` -- i.e. it just needs kodi-dev to
be in the sysroot (a normal `DEPENDS`), no special integration required. This
was verified directly against real buildhistory output before porting the
mechanism, not assumed.

## A recurring class of cmake cross-compile bug worth knowing about

Two real bugs turned up while porting the addon-build mechanism, both the same
underlying shape: a recipe/patch computes an **already-resolved absolute
sysroot path** (from `find_package()`, or from `CMAKE_INSTALL_PREFIX` under a
misunderstanding of what that variable actually holds) and bakes it into a
file meant to be consumed by a *different, later* build -- tripping
`do_package_qa`'s `buildpaths` check, correctly, because the embedded path is
real but only valid for the build machine and moment that produced it.

The right fix, in both cases actually found here (see `kodi-platform`'s
history in `addons.md`), is **deferred resolution**: leave a literal,
unexpanded `${CMAKE_FIND_ROOT_PATH}` reference in the generated file, so
whichever build later consumes it expands the variable against *its own*
sysroot. `INSANE_SKIP` is not the fix for this class of bug even when the
embedded path happens to be harmless in practice (e.g. a build-time-only SDK
artifact never shipped to a target) -- chase the real deferred-path mechanism
first; both instances here turned out to be genuinely fixable.
