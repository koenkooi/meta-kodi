SUMMARY = "Kodi Media Center"
DESCRIPTION = "Kodi is an award-winning free and open source home theater/media \ 
center software and entertainment hub for digital media. With its beautiful \
interface and powerful skinning engine, it's available for Android, BSD, Linux, \
macOS, iOS and Windows."

HOMEPAGE = "https://kodi.tv/"
BUGTRACKER = "https://github.com/xbmc/xbmc/issues"

require ${BPN}.inc
inherit cmake gettext python3-dir python3native

SRC_URI_append = " \
	file://0001-FindCrossGUID.cmake-fix-for-crossguid-0.2.x.patch \
"

OECMAKE_FIND_ROOT_PATH_MODE_PROGRAM = "BOTH"

DEPENDS += " \
  curl-native \
  flatbuffers-native \
  googletest-native \
  gperf-native \
  kodi-tools-jsonschemabuilder-native \
  kodi-tools-texturepacker-native \
  nasm-native \
  swig-native \
  unzip-native \
  yasm-native \
  zip-native \
  \
  avahi \
  boost \
  bzip2 \
  crossguid \
  curl \
  dcadec \
  enca \
  expat \
  faad2 \
  ffmpeg \
  flatbuffers \
  fmt \
  fontconfig \
  fribidi \
  fstrcmp \
  giflib \
  libass \
  libcdio \
  libcec \
  libdvdcss \
  libdvdnav \
  libdvdread \
  libinput \
  libmad \
  libmicrohttpd \
  libmms \
  libmodplug \
  libnfs \
  libpcre \
  libplist \
  libsamplerate0 \
  libssh \
  libtinyxml \
  libusb1 \
  libxkbcommon \
  libxslt \
  lzo \
  mpeg2dec \
  python3 \
  rapidjson \
  spdlog \
  sqlite3 \
  taglib \
  udev \
  virtual/egl \
  wavpack \
  yajl \
  zlib \
"

# breaks compilation
CCACHE_DISABLE = "1"
ASNEEDED = ""

KODIVAAPIDEPENDS = "libva"
KODIVAAPIDEPENDS_append_x86 = " intel-vaapi-driver"
KODIVAAPIDEPENDS_append_x86-64 = " intel-vaapi-driver"

PACKAGECONFIG ?= " \
  ${@bb.utils.contains('VAAPISUPPORT', '1', 'vaapi', '', d)} \
  ${@bb.utils.contains('VDPAUSUPPORT', '1', 'vdpau', '', d)} \
  ${@bb.utils.filter('DISTRO_FEATURES', 'bluetooth lirc pulseaudio samba systemd', d)} \
  ${@bb.utils.filter('KODIGRAPHICALBACKEND', 'gbm wayland x11', d)} \
  airtunes \
  joystick \
  lcms \
"

# Core windowing system choices

PACKAGECONFIG[gbm] = "-DCORE_PLATFORM_NAME=gbm -DGBM_RENDER_SYSTEM=gles,,"
PACKAGECONFIG[wayland] = "-DCORE_PLATFORM_NAME=wayland -DWAYLAND_RENDER_SYSTEM=gles,,wayland waylandpp"
PACKAGECONFIG[x11] = "-DCORE_PLATFORM_NAME=x11,,libxinerama libxmu libxrandr libxtst glew"

# Features

PACKAGECONFIG[airtunes] = "-DENABLE_AIRTUNES=ON,-DENABLE_AIRTUNES=OFF"
PACKAGECONFIG[bluetooth] = ",,bluez5"
PACKAGECONFIG[dvdcss] = "-DENABLE_DVDCSS=ON,-DENABLE_DVDCSS=OFF"
PACKAGECONFIG[joystick] = ",,,kodi-addon-peripheral-joystick"
PACKAGECONFIG[lcms] = ",,lcms"
PACKAGECONFIG[lirc] = ",,lirc"
PACKAGECONFIG[mysql] = "-DENABLE_MYSQLCLIENT=ON,-DENABLE_MYSQLCLIENT=OFF,mysql5"
PACKAGECONFIG[optical] = "-DENABLE_OPTICAL=ON,-DENABLE_OPTICAL=OFF"
PACKAGECONFIG[pulseaudio] = "-DENABLE_PULSEAUDIO=ON,-DENABLE_PULSEAUDIO=OFF,pulseaudio"
PACKAGECONFIG[samba] = ",,samba"
PACKAGECONFIG[systemd] = ",,,kodi-systemd-service"
PACKAGECONFIG[vaapi] = "-DENABLE_VAAPI=ON,-DENABLE_VAAPI=OFF,${KODIVAAPIDEPENDS},${KODIVAAPIDEPENDS}"
PACKAGECONFIG[vdpau] = "-DENABLE_VDPAU=ON,-DENABLE_VDPAU=OFF,libvdpau,mesa-vdpau-drivers"

# Compilation tunes

PACKAGECONFIG[gold] = "-DENABLE_LDGOLD=ON,-DENABLE_LDGOLD=OFF"
PACKAGECONFIG[lto] = "-DUSE_LTO=${@oe.utils.cpu_count()},-DUSE_LTO=OFF"
PACKAGECONFIG[testing] = "-DENABLE_TESTING=ON,-DENABLE_TESTING=0FF,googletest"

# MIPS

LDFLAGS += "${TOOLCHAIN_OPTIONS}"
LDFLAGS_append_mipsarch = " -latomic -lpthread"
EXTRA_OECMAKE_append_mipsarch = " -DWITH_ARCH=${TARGET_ARCH}"

#| cmake/scripts/common/Platform.cmake:11 (message):
#|   You need to decide whether you want to use GL- or GLES-based rendering.
#|   Please set APP_RENDER_SYSTEM to either "gl" or "gles".
#|   For embedded systems you will usually want to use "gles".

KODI_OPENGL_STANDARD ?= "gles"

EXTRA_OECMAKE = " \
    -DAPP_RENDER_SYSTEM=${KODI_OPENGL_STANDARD} \
    \
    -DENABLE_INTERNAL_CROSSGUID=OFF \
    \
    -DNATIVEPREFIX=${STAGING_DIR_NATIVE}${prefix} \
    -DJava_JAVA_EXECUTABLE=/usr/bin/java \
    -DWITH_TEXTUREPACKER=${STAGING_BINDIR_NATIVE}/TexturePacker \
    -DWITH_JSONSCHEMABUILDER=${STAGING_BINDIR_NATIVE}/JsonSchemaBuilder \
    \
    -DENABLE_STATIC_LIBS=FALSE \
    -DCMAKE_NM='${NM}' \
    \
    -DFFMPEG_PATH=${STAGING_DIR_TARGET} \
    -DLIBDVD_INCLUDE_DIRS=${STAGING_INCDIR} \
    -DNFS_INCLUDE_DIR=${STAGING_INCDIR} \
    -DSHAIRPLAY_INCLUDE_DIR=${STAGING_INCDIR} \
    -DCMAKE_BUILD_TYPE=RelWithDebInfo \
"

# OECMAKE_GENERATOR="Unix Makefiles"
#PARALLEL_MAKE = " "

FULL_OPTIMIZATION_armv7a = "-fexpensive-optimizations -fomit-frame-pointer -O4 -ffast-math"
BUILD_OPTIMIZATION = "${FULL_OPTIMIZATION}"

# for python modules
export HOST_SYS
export BUILD_SYS
export STAGING_LIBDIR
export STAGING_INCDIR
export PYTHON_DIR

export TARGET_PREFIX

do_configure_prepend() {
	# Ensure 'nm' can find the lto plugins 
	liblto=$(find ${STAGING_DIR_NATIVE} -name "liblto_plugin.so.0.0.0")
	mkdir -p ${STAGING_LIBDIR_NATIVE}/bfd-plugins
	ln -sf $liblto ${STAGING_LIBDIR_NATIVE}/bfd-plugins/liblto_plugin.so

	sed -i -e 's:CMAKE_NM}:}${TARGET_PREFIX}gcc-nm:' ${S}/xbmc/cores/DllLoader/exports/CMakeLists.txt
}

INSANE_SKIP_${PN} = "rpaths"

FILES_${PN} += "${datadir}/xsessions ${datadir}/icons ${libdir}/xbmc ${datadir}/xbmc ${libdir}/firewalld"
FILES_${PN}-dbg += "${libdir}/kodi/.debug ${libdir}/kodi/*/.debug ${libdir}/kodi/*/*/.debug ${libdir}/kodi/*/*/*/.debug"

# kodi uses some kind of dlopen() method for libcec so we need to add it manually
# OpenGL builds need glxinfo, that's in mesa-demos
RRECOMMENDS_${PN}_append = " \
  libcec \
  libcurl \
  libnfs \
  ${@bb.utils.contains('PACKAGECONFIG', 'x11', 'xdyinfo xrandr xinit mesa-demos', '', d)} \
  python3 \
  python3-compression \
  python3-ctypes \
  python3-difflib \
  python3-html \
  python3-json \
  python3-netclient \
  python3-regex \
  python3-shell \
  python3-sqlite3 \
  python3-xmlrpc \
  tzdata-africa \
  tzdata-americas \
  tzdata-antarctica \
  tzdata-arctic \
  tzdata-asia \
  tzdata-atlantic \
  tzdata-australia \
  tzdata-europe \
  tzdata-pacific \
  xkeyboard-config \
"

RRECOMMENDS_${PN}_append_libc-glibc = " \
  glibc-charmap-ibm850 \
  glibc-gconv-ibm850 \
  glibc-charmap-ibm437 \
  glibc-gconv-ibm437 \
  glibc-gconv-unicode \
  glibc-gconv-utf-32 \
  glibc-charmap-utf-8 \
  glibc-localedata-en-us \
"
