SUMMARY = "Kodi Media Center"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=930e2a5f63425d8dd72dbd7391c43c46"

DEPENDS = " \
            cmake-native \
            curl-native \
            gperf-native \
            jsonschemabuilder-native \
            nasm-native \
            swig-native \
            yasm-native \
            zip-native \
            avahi \
            boost \
            bzip2 \
            curl \
            dcadec \
            enca \
            expat \
            faad2 \
            ffmpeg \
            fontconfig \
            fribidi \
            giflib \
            jasper \
            libass \
            libcdio \
            libcec \
            libmad \
            libmicrohttpd \
            libmms \
            libmms \
            libmodplug \
            libpcre \
            libplist \
            libsamplerate0 \
            libsdl-image \
            libsdl-mixer \
            libsquish \
            libssh \
            libtinyxml \
            libusb1 \
            libxinerama \
            libxmu \
            libxrandr \
            libxslt \
            libxtst \
            lzo \
            mpeg2dec \
            python \
            samba \
            sqlite3 \
            taglib \
            virtual/egl \
            virtual/libsdl \
            wavpack \
            yajl \
            zlib \
          "

SRCREV = "c327c53ac5346f71219e8353fe046e43e4d4a827"

PV = "16.1+gitr${SRCPV}"
SRC_URI = "git://github.com/xbmc/xbmc.git;branch=Jarvis \
           file://0001-configure-don-t-try-to-run-stuff-to-find-tinyxml.patch \
           file://0002-arm64-Fix-build-breakages-due-to-architecture-specif.patch \
           file://0003-configure-add-aarch64-support.patch \
           file://0004-configure-remove-gles-neon-wayland-assumptions.patch \
           file://0005-handle-SIGTERM.patch \
           file://0006-add-support-to-read-frequency-output-if-using-intel-.patch \
           file://0007-https-github.com-OpenELEC-OpenELEC.tv-blob-master-pa.patch \
"

inherit autotools-brokensep gettext python-dir

S = "${WORKDIR}/git"

# breaks compilation
CCACHE = ""
ASNEEDED = ""

CACHED_CONFIGUREVARS += " \
    ac_cv_path_PYTHON="${STAGING_BINDIR_NATIVE}/python-native/python" \
"

ACCEL ?= ""
ACCEL_x86 = "vaapi vdpau"
ACCEL_x86-64 = "vaapi vdpau"

PACKAGECONFIG ??= "${ACCEL} opengl"
PACKAGECONFIG[opengl] = "--enable-gl,--enable-gles,glew"
PACKAGECONFIG[openglesv2] = "--enable-gles,--enable-gl,"
PACKAGECONFIG[vaapi] = "--enable-vaapi,--disable-vaapi,libva"
PACKAGECONFIG[vdpau] = "--enable-vdpau,--disable-vdpau,libvdpau"
PACKAGECONFIG[mysql] = "--enable-mysql,--disable-mysql,mysql5"
PACKAGECONFIG[wayland] = "--enable-wayland,--disable-wayland,wayland"

EXTRA_OECONF = " \
    --enable-libusb \
    --enable-airplay \
    --disable-optical-drive \
    --with-ffmpeg=shared \
    --enable-texturepacker=no \
"

FULL_OPTIMIZATION_armv7a = "-fexpensive-optimizations -fomit-frame-pointer -O4 -ffast-math"
BUILD_OPTIMIZATION = "${FULL_OPTIMIZATION}"

# for python modules
export HOST_SYS
export BUILD_SYS
export STAGING_LIBDIR
export STAGING_INCDIR
export PYTHON_DIR

do_configure() {
    ( for i in $(find ${S} -name "configure.*" ) ; do
	cd $(dirname $i) && gnu-configize --force || true
    done )

    make -C tools/depends/target/crossguid PREFIX=${STAGING_DIR_HOST}${prefix}
    BOOTSTRAP_STANDALONE=1 make -f bootstrap.mk JSON_BUILDER="${STAGING_BINDIR_NATIVE}/JsonSchemaBuilder" 
    BOOTSTRAP_STANDALONE=1 make -f codegenerator.mk JSON_BUILDER="${STAGING_BINDIR_NATIVE}/JsonSchemaBuilder" 
    oe_runconf
}

do_compile_prepend() {
    for i in $(find . -name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' $i
    done

    for i in $(find . -name "*.mak*" -o    -name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' -e 's:-rpath \$(libdir):-rpath ${libdir}:g' $i
    done
}

INSANE_SKIP_${PN} = "rpaths"

FILES_${PN} += "${datadir}/xsessions ${datadir}/icons ${libdir}/xbmc ${datadir}/xbmc"
FILES_${PN}-dbg += "${libdir}/kodi/.debug ${libdir}/kodi/*/.debug ${libdir}/kodi/*/*/.debug ${libdir}/kodi/*/*/*/.debug"

# kodi uses some kind of dlopen() method for libcec so we need to add it manually
# OpenGL builds need glxinfo, that's in mesa-demos
RRECOMMENDS_${PN}_append = " libcec \
                             python \
                             python-lang \
                             python-re \
                             python-netclient \
                             python-html \
                             python-difflib \
                             python-json \
                             python-zlib \
                             python-shell \
                             python-sqlite3 \
                             python-compression \
                             libcurl \
                             xdpyinfo \
                             xrandr \
                             ${@base_contains('PACKAGECONFIG', 'opengl', 'mesa-demos', '', d)} \
"
RRECOMMENDS_${PN}_append_libc-glibc = " glibc-charmap-ibm850 \
                                        glibc-gconv-ibm850 \
					glibc-gconv-unicode \
                                        glibc-gconv-utf-32 \
					glibc-charmap-utf-8 \
					glibc-localedata-en-us \
                                      "
