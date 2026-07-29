SUMMARY = "Kodi media center"
DESCRIPTION = "Kodi is an award-winning free and open source home theater/media \
center software and entertainment hub for digital media. With its beautiful \
interface and powerful skinning engine, it's available for Android, BSD, Linux, \
macOS, iOS and Windows."
HOMEPAGE = "https://kodi.tv/"
BUGTRACKER = "https://github.com/xbmc/xbmc/issues"

require kodi-source.inc

# Required dependencies as of Kodi 22 (CMakeLists.txt required_deps),
# plus the native tools it needs (FlatC comes from flatbuffers-native;
# the interface codegen in xbmc/interfaces/swig runs SWIG then a
# Groovy script under openjdk-8-native, from meta-java). The autotools
# natives are for the private libdvdnav/libdvdread sub-builds.
DEPENDS += "\
    autoconf-native \
    automake-native \
    crossguid \
    curl \
    exiv2 \
    ffmpeg \
    flatbuffers \
    flatbuffers-native \
    fmt \
    freetype \
    fribidi \
    fstrcmp \
    harfbuzz \
    kodi-jsonschemabuilder-native \
    kodi-texturepacker-native \
    libass \
    libpcre2 \
    libtinyxml \
    libtinyxml2 \
    libtool-native \
    lzo \
    nlohmann-json \
    openjdk-8-native \
    openssl \
    python3 \
    spdlog \
    sqlite3 \
    swig-native \
    taglib \
    virtual/libiconv \
    zlib \
"

# Kodi's private libdvdnav/libdvdread/libdvdcss forks, always linked even
# with ENABLE_OPTICAL=OFF. Tags from tools/depends/target/libdvd*/*-VERSION
# at 22.0b1-Piers. Kodi feeds the *_URL values below to
# ExternalProject_Add(URL ...), which copies a local directory as-is, so
# plain git checkouts work and no tarball is needed.
LIBDVDNAV_VERSION = "6.1.1-Next-Nexus-Alpha2-2"
LIBDVDREAD_VERSION = "6.1.3-Next-Nexus-Alpha2-2"
LIBDVDCSS_VERSION = "1.4.3-Next-Nexus-Alpha2-2"

SRC_URI += "\
    git://github.com/xbmc/libdvdnav.git;protocol=https;nobranch=1;name=libdvdnav;tag=${LIBDVDNAV_VERSION};destsuffix=libdvdnav \
    git://github.com/xbmc/libdvdread.git;protocol=https;nobranch=1;name=libdvdread;tag=${LIBDVDREAD_VERSION};destsuffix=libdvdread \
    git://github.com/xbmc/libdvdcss.git;protocol=https;nobranch=1;name=libdvdcss;tag=${LIBDVDCSS_VERSION};destsuffix=libdvdcss \
    file://kodi.service \
    file://0001-cmake-sysroot-prefix-wayland-pkgdatadir.patch \
    file://0002-cmake-sysroot-in-core_link_library.patch \
"
SRCREV_libdvdnav = "25229a54b2c4be9930d677f6abe3a0025514f3db"
SRCREV_libdvdread = "329ec88257ecaf64a3144644a423289bb3da8ef6"
SRCREV_libdvdcss = "d818bc07941ad4095703e8dc04c6c8ea67a7f814"
SRCREV_FORMAT = "default_libdvdnav_libdvdread_libdvdcss"

# Groovy runtime and Apache commons jars for the SWIG interface codegen,
# fetched by bitbake instead of CMake's configure-time file(DOWNLOAD)
# (tasks other than do_fetch have no network). Versions and sha512sums
# track xbmc/interfaces/swig/CMakeLists.txt; the *_SOURCE_DIR CMake
# variables below make it skip the downloads entirely.
GROOVY_VERSION = "4.0.30"
COMMONS_LANG_VERSION = "3.20.0"
COMMONS_TEXT_VERSION = "1.15.0"
SRC_URI += "\
    https://mirrors.kodi.tv/build-deps/sources/apache-groovy-binary-${GROOVY_VERSION}.zip;name=groovy \
    https://mirrors.kodi.tv/build-deps/sources/commons-lang3-${COMMONS_LANG_VERSION}-bin.tar.gz;name=commonslang \
    https://mirrors.kodi.tv/build-deps/sources/commons-text-${COMMONS_TEXT_VERSION}-bin.tar.gz;name=commonstext \
"
SRC_URI[groovy.sha512sum] = "789e956ecad3c302d545a16caccd45454357556b566ee83abfafe102371cf218f5ed5dc83d748595fbb2d465bde6c7306a498806e58b512cfc742abe5f5b101d"
SRC_URI[commonslang.sha512sum] = "eead2c04a85c85cfc83ef8a858f954e7abf668367d5a42e1152eb5d656eb8f4c1d5e7ad03f46859e99393e92b0d5fc69c927301c1a2ca466a3c49fb1f186ce89"
SRC_URI[commonstext.sha512sum] = "6d1fcbd85a85f38e95ea33c9ab7f58798723b0e6357077246a135c417e75c7b39e278ebdb77fcc8f9b56b52b1eeff113eea9463eaaaee8d38df9afdc149b6421"

inherit cmake pkgconfig gettext python3native python3targetconfig features_check useradd systemd

# EGL is required by every windowing backend; mesa only provides it with
# the opengl distro feature enabled.
REQUIRED_DISTRO_FEATURES = "opengl"

# Windowing backends: any combination of gbm/wayland/x11 goes into one
# binary, selectable at runtime (kodi --windowing=<backend>). Render
# system: exactly one of gl/gles; gles is the right default for the
# mesa-driven embedded GPUs this layer targets (freedreno, lima,
# panfrost) and Kodi 22 accepts it with all three backends.
PACKAGECONFIG ?= "\
    gbm \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', '', d)} \
    gles \
    alsa avahi cec dbus microhttpd nfs udev upnp \
    ${@bb.utils.filter('DISTRO_FEATURES', 'bluetooth pipewire pulseaudio systemd', d)} \
"

# Windowing backends contribute no CMake arguments directly;
# CORE_PLATFORM_NAME is computed from the selected set below.
# Dependencies follow Kodi's cmake/platform/linux/<backend>.cmake
# required dependency lists.
PACKAGECONFIG[gbm] = ",,virtual/egl virtual/libgbm libdisplay-info libdrm libinput libxkbcommon udev"
PACKAGECONFIG[wayland] = ",,virtual/egl libdrm libxkbcommon wayland wayland-native wayland-protocols waylandpp waylandpp-native"
PACKAGECONFIG[x11] = ",,virtual/egl libdrm libxrandr virtual/libx11"

PACKAGECONFIG[gl] = "-DAPP_RENDER_SYSTEM=gl,,virtual/libgl"
PACKAGECONFIG[gles] = "-DAPP_RENDER_SYSTEM=gles,,virtual/libgles2"

PACKAGECONFIG[airtunes] = "-DENABLE_AIRTUNES=ON,-DENABLE_AIRTUNES=OFF,shairplay"
PACKAGECONFIG[alsa] = "-DENABLE_ALSA=ON,-DENABLE_ALSA=OFF,alsa-lib"
PACKAGECONFIG[avahi] = "-DENABLE_AVAHI=ON,-DENABLE_AVAHI=OFF,avahi"
PACKAGECONFIG[bluetooth] = "-DENABLE_BLUETOOTH=ON,-DENABLE_BLUETOOTH=OFF,bluez5"
PACKAGECONFIG[cap] = "-DENABLE_CAP=ON,-DENABLE_CAP=OFF,libcap"
PACKAGECONFIG[cec] = "-DENABLE_CEC=ON,-DENABLE_CEC=OFF,libcec"
PACKAGECONFIG[dbus] = "-DENABLE_DBUS=ON,-DENABLE_DBUS=OFF,dbus"
PACKAGECONFIG[dvdcss] = "-DENABLE_DVDCSS=ON -Dlibdvdcss_URL=${UNPACKDIR}/libdvdcss,-DENABLE_DVDCSS=OFF"
PACKAGECONFIG[lcms] = "-DENABLE_LCMS2=ON,-DENABLE_LCMS2=OFF,lcms"
PACKAGECONFIG[lirc] = "-DENABLE_LIRCCLIENT=ON,-DENABLE_LIRCCLIENT=OFF,lirc"
PACKAGECONFIG[lto] = "-DUSE_LTO=${@oe.utils.cpu_count()},-DUSE_LTO=OFF"
PACKAGECONFIG[mariadb] = "-DENABLE_MARIADBCLIENT=ON,-DENABLE_MARIADBCLIENT=OFF,mariadb"
PACKAGECONFIG[microhttpd] = "-DENABLE_MICROHTTPD=ON,-DENABLE_MICROHTTPD=OFF,libmicrohttpd"
PACKAGECONFIG[nfs] = "-DENABLE_NFS=ON,-DENABLE_NFS=OFF,libnfs"
PACKAGECONFIG[optical] = "-DENABLE_OPTICAL=ON,-DENABLE_OPTICAL=OFF,libcdio libudfread"
PACKAGECONFIG[pipewire] = "-DENABLE_PIPEWIRE=ON,-DENABLE_PIPEWIRE=OFF,pipewire"
PACKAGECONFIG[plist] = "-DENABLE_PLIST=ON,-DENABLE_PLIST=OFF,libplist"
PACKAGECONFIG[pulseaudio] = "-DENABLE_PULSEAUDIO=ON,-DENABLE_PULSEAUDIO=OFF,pulseaudio"
PACKAGECONFIG[samba] = "-DENABLE_SMBCLIENT=ON,-DENABLE_SMBCLIENT=OFF,samba"
PACKAGECONFIG[systemd] = ",,systemd"
PACKAGECONFIG[udev] = "-DENABLE_UDEV=ON,-DENABLE_UDEV=OFF,udev"
PACKAGECONFIG[upnp] = "-DENABLE_UPNP=ON,-DENABLE_UPNP=OFF"
PACKAGECONFIG[vaapi] = "-DENABLE_VAAPI=ON,-DENABLE_VAAPI=OFF,libva"
PACKAGECONFIG[vdpau] = "-DENABLE_VDPAU=ON,-DENABLE_VDPAU=OFF,libvdpau"

# Sanity-check the windowing/render selection at parse time.
python __anonymous() {
    pkgconfig = (d.getVar('PACKAGECONFIG') or "").split()
    if not any(w in pkgconfig for w in ('gbm', 'wayland', 'x11')):
        bb.fatal("kodi: at least one windowing backend PACKAGECONFIG (gbm, wayland, x11) must be enabled")
    render = [r for r in ('gl', 'gles') if r in pkgconfig]
    if len(render) != 1:
        bb.fatal("kodi: exactly one render system PACKAGECONFIG (gl or gles) must be enabled, got: %s" % (' '.join(render) or "none"))
}

# Semicolon-separated CMake list (Kodi's Platform.cmake wants this, not
# spaces). Backslash-escaped: EXTRA_OECMAKE lands in an unquoted shell
# command, where a bare ";" is a command separator, not just a CMake one.
KODI_CORE_PLATFORM_NAME = "${@'\\;'.join(bb.utils.filter('PACKAGECONFIG', 'gbm wayland x11', d).split())}"

# ENABLE_APP_AUTONAME=OFF keeps the binary named kodi.bin even in
# single-backend builds, so packaging and the systemd unit do not
# depend on the selected backends.
EXTRA_OECMAKE = "\
    -DCORE_PLATFORM_NAME=${KODI_CORE_PLATFORM_NAME} \
    -DENABLE_APP_AUTONAME=OFF \
    -DENABLE_INTERNAL_CROSSGUID=OFF \
    -DENABLE_INTERNAL_FFMPEG=OFF \
    -DFFMPEG_PATH=${STAGING_EXECPREFIXDIR} \
    -DENABLE_TESTING=OFF \
    -DENABLE_GOLD=OFF \
    -DWITH_TEXTUREPACKER=${STAGING_BINDIR_NATIVE} \
    -DWITH_JSONSCHEMABUILDER=${STAGING_BINDIR_NATIVE} \
    -Dlibdvdnav_URL=${UNPACKDIR}/libdvdnav \
    -Dlibdvdread_URL=${UNPACKDIR}/libdvdread \
    -Dgroovy_SOURCE_DIR=${UNPACKDIR}/groovy-${GROOVY_VERSION} \
    -Dapache-commons-lang_SOURCE_DIR=${UNPACKDIR}/commons-lang3-${COMMONS_LANG_VERSION} \
    -Dapache-commons-text_SOURCE_DIR=${UNPACKDIR}/commons-text-${COMMONS_TEXT_VERSION} \
"

do_install:append() {
    if ${@bb.utils.contains('PACKAGECONFIG', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${UNPACKDIR}/kodi.service ${D}${systemd_system_unitdir}/kodi.service
    fi
}

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "--system --create-home --home-dir /home/kodi --shell /bin/sh --user-group kodi"

SYSTEMD_SERVICE:${PN} = "${@bb.utils.contains('PACKAGECONFIG', 'systemd', 'kodi.service', '', d)}"
SYSTEMD_AUTO_ENABLE ?= "enable"

FILES:${PN} += "\
    ${datadir}/icons \
    ${datadir}/metainfo \
    ${datadir}/wayland-sessions \
    ${datadir}/xsessions \
    ${libdir}/firewalld \
"

# libxkbcommon (gbm and wayland backends) needs the keymap data at
# runtime; python3-modules covers the stdlib pieces Kodi's bundled
# Python addons import at will.
RRECOMMENDS:${PN} += "\
    python3-modules \
    tzdata \
    xkeyboard-config \
"

# Kodi's charset converter iconv_open()s UTF-32 at runtime
RRECOMMENDS:${PN}:append:libc-glibc = " glibc-gconv-utf-32"
