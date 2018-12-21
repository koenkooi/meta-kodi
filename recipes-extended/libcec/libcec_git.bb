SUMMARY = "USB CEC Adaptor communication Library"
HOMEPAGE = "http://libcec.pulse-eight.com/"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=e61fd86f9c947b430126181da2c6c715"

DEPENDS = "p8platform udev lockdev ncurses swig-native python3"

DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'libx11 libxrandr', '', d)}"
DEPENDS_append_rpi = "${@bb.utils.contains('MACHINE_FEATURES', 'vc4graphics', '', ' userland', d)}"

PV = "4.0.3+git${SRCPV}"

SRCREV = "f7593243377687c84c27b4e0342b00bb2ef2efbc"
SRC_URI = "git://github.com/Pulse-Eight/libcec.git;branch=master \
           file://0001-Add-Linux-CEC-Adapter.patch \
          "

S = "${WORKDIR}/git"

inherit cmake pkgconfig

EXTRA_OECMAKE = "-DHAVE_LINUX_API=1 -DCMAKE_INSTALL_LIBDIR=${libdir} -DCMAKE_INSTALL_LIBDIR_NOARCH=${libdir}"

# Create the wrapper for python3
PACKAGES =+ "python3-libcec"
FILES_python3-libcec = "${libdir}/python3* ${bindir}/py*"
RDEPENDS_python3-libcec += "python3-core"

# cec-client and xbmc need the .so present to work :(
FILES_${PN} += "${libdir}/*.so"
INSANE_SKIP_${PN} = "dev-so"

# Adapter shows up as a CDC-ACM device
RRECOMMENDS_${PN} = "kernel-module-cdc-acm"
