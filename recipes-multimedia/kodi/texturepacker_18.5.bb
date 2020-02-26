SUMMARY = "Kodi Media Center"

require kodi.inc
inherit cmake gettext python-dir pythonnative

DEFAULT_KODI_WORKDIR = "${WORKDIR}/git/tools/depends/native/TexturePacker"

DEPENDS = " \
    giflib \
    jpeg \
    libpng \
    lzo \
"

OECMAKE_CXX_FLAGS_append = " -DTARGET_POSIX -std=gnu++11 -I${WORKDIR}/git/xbmc/linux"

do_configure_prepend() {
    ln -sf ${WORKDIR}/git/xbmc ${WORKDIR}/git/tools/depends/native/TexturePacker/
}

EXTRA_OECMAKE = "-DCMAKE_MODULE_PATH=${WORKDIR}/git/cmake/modules"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/TexturePacker ${D}${bindir}
}

BBCLASSEXTEND = "native"
