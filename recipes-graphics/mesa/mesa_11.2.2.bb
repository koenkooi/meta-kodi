require recipes-graphics/mesa/${BPN}.inc

SRCREV = "5de088f7da75cc0209ff1602ed70aff14f733e4b"
SRC_URI = "git://anongit.freedesktop.org/git/mesa/mesa.git;protocol=http;branch=11.2"

S = "${WORKDIR}/git"

DEPENDS += "python-mako-native"

inherit pythonnative

#because we cannot rely on the fact that all apps will use pkgconfig,
#make eglplatform.h independent of MESA_EGL_NO_X11_HEADER
do_install_append() {
    if ${@bb.utils.contains('PACKAGECONFIG', 'egl', 'true', 'false', d)}; then
        sed -i -e 's/^#if defined(MESA_EGL_NO_X11_HEADERS)$/#if defined(MESA_EGL_NO_X11_HEADERS) || ${@bb.utils.contains('PACKAGECONFIG', 'x11', '0', '1', d)}/' ${D}${includedir}/EGL/eglplatform.h
    fi
}
