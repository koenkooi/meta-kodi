require recipes-graphics/mesa/${BPN}.inc

LIC_FILES_CHKSUM = "file://docs/license.html;md5=899fbe7e42d494c7c8c159c7001693d5"

SRCREV = "edfc17a19a17e8dae41a703a9cb4b9eb3c255c34"
SRC_URI = "git://anongit.freedesktop.org/git/mesa/mesa.git;protocol=http;branch=12.0 \
           file://0001-freedreno-disable-error-check-to-make-it-continue.patch \
          "

S = "${WORKDIR}/git"

DEPENDS += "python-mako-native llvm3.3"

inherit pythonnative

#because we cannot rely on the fact that all apps will use pkgconfig,
#make eglplatform.h independent of MESA_EGL_NO_X11_HEADER
do_install_append() {
    if ${@bb.utils.contains('PACKAGECONFIG', 'egl', 'true', 'false', d)}; then
        sed -i -e 's/^#if defined(MESA_EGL_NO_X11_HEADERS)$/#if defined(MESA_EGL_NO_X11_HEADERS) || ${@bb.utils.contains('PACKAGECONFIG', 'x11', '0', '1', d)}/' ${D}${includedir}/EGL/eglplatform.h
    fi
}
