# meta-kodi

An OpenEmbedded application layer providing the [Kodi](https://kodi.tv/) media
center and its supporting recipes.

This branch (`wrynose`) is a from-scratch rewrite of the layer targeting the
openembedded-core `wrynose` release series and Kodi 22 "Piers". Earlier
branches of this repository (jethro through master/Kodi 20 "Nexus") are kept
for reference but share no history with this branch.

## Kodi version

The `kodi` recipe builds the real upstream 22.0 beta 1 pre-release:

* tag: [`22.0b1-Piers`](https://github.com/xbmc/xbmc/releases/tag/22.0b1-Piers)
* commit: `77395cf42e141000b0475b8d5a54925ad67bfeba`

## Layer dependencies

* openembedded-core (`wrynose`)
* meta-openembedded/meta-oe (waylandpp, libtinyxml, libtinyxml2, libass,
  giflib, exiv2, nlohmann-json, libcec, libnfs, spdlog, flatbuffers, ...)
* meta-openembedded/meta-multimedia (crossguid, dav1d, libao)

## Windowing and rendering design

Kodi selects its windowing backends at build time with the CMake variable
`CORE_PLATFORM_NAME` (any combination of `gbm`, `wayland`, `x11`) and its
render system with `APP_RENDER_SYSTEM` (exactly one of `gl` or `gles`).

The recipe maps this onto `PACKAGECONFIG`:

* `gbm`, `wayland`, `x11` — windowing backends, freely combinable. All
  enabled backends are compiled into a single `kodi.bin`; the backend is
  chosen at runtime (`kodi --windowing=<backend>`). At least one must be
  enabled. The default enables `gbm` unconditionally and adds `wayland`
  and/or `x11` when the corresponding `DISTRO_FEATURES` are present.
* `gl` / `gles` — render system, exactly one must be enabled. The default
  is `gles`, which is the right answer for the mesa-driven embedded GPUs
  this layer targets (freedreno, lima, panfrost) and is accepted by all
  three windowing backends in Kodi 22 (including x11).

Per-backend dependencies follow Kodi's own `cmake/platform/linux/*.cmake`
requirement lists: gbm pulls in libdisplay-info, libinput, libxkbcommon and
udev; wayland pulls in waylandpp (plus its native scanner) and
wayland-protocols; x11 pulls in libx11 and libxrandr. All three need libdrm
and EGL.

The whole recipe is gated on the `opengl` distro feature via
`features_check`, matching how other GL-consuming recipes in
openembedded-core handle this.

## Board support notes

meta-kodi is an application layer: it provides recipes that work with
whatever GPU/graphics stack a machine configuration brings, it does not
define machines itself.

* **QCM2290/QRB2210 boards** (`rb1-core-kit`, `uno-q`; Adreno 702): machine
  configurations exist in meta-qcom and meta-qcom-3rdparty. meta-qcom
  enables the mesa freedreno driver for all `qcom` machines, so the default
  `gbm + gles` configuration works, with `wayland` added when the distro
  enables it.
* **QCS6490 boards** (`rb3gen2-core-kit`, `qcm6490-idp`,
  `radxa-dragon-q6a`; Adreno 643): same freedreno-based stack via
  `conf/machine/include/qcom-qcs6490.inc`, same Kodi configuration applies.
* **Amlogic S905X boards** (Libre Computer Le Potato, Khadas VIM, ...;
  Mali-450 via the mesa lima driver): no machine configurations exist in
  this project yet; the meta-meson layer provides them (machines
  `amlogic-s905x`, `libretech-cc`, `khadas-vim`, ...) and enables
  `lima panfrost` in mesa. lima only provides OpenGL ES 2.0, so `gles` is
  mandatory there; `gbm` and `wayland` are the sensible backends. Note that
  meta-meson `master` currently declares `LAYERSERIES_COMPAT` for
  walnascar/whinlatter, not yet wrynose.

## Build host requirements

Generating Kodi's Python bindings runs a Groovy code generator on the build
host, so a Java runtime (`java` in `PATH`, exposed via `HOSTTOOLS`) is
required:

    HOSTTOOLS += "java"

## Optical media

Kodi unconditionally links a private libdvdnav/libdvdread (built from
Kodi's own pinned forks). The recipe pre-fetches those tarballs through
`SRC_URI` and points the build at the local copies, so no network access
happens during configure/compile. CSS decryption (`dvdcss`) and full
optical drive support (`optical`) are separate `PACKAGECONFIG` options;
Blu-ray support (libbluray) is not wired up yet because no current recipe
for it exists in openembedded-core or meta-openembedded.

## Contributing

Send pull requests to https://github.com/koenkooi/meta-kodi
