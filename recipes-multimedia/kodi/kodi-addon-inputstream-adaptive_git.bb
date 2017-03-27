SUMMARY = "kodi inputstream addon for several manifest types"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://src/main.cpp;beginline=1;endline=17;md5=5eac1e215251c8f88d799f80ed45d5c0"

DEPENDS = " \
            zip-native \
            p8platform \
            kodi-platform \
          "

SRCREV = "d248490ba4ac384c377b4aa420b7ad4c87fa0928"


PV = "1.0.6+gitr${SRCPV}"
SRC_URI = "git://github.com/peak3d/inputstream.adaptive.git;protocol=https \
          "

inherit cmake pkgconfig gettext

S = "${WORKDIR}/git"

EXTRA_OECMAKE = " \
	  -DADDONS_TO_BUILD=inputstream.adaptive \
	  -DADDON_SRC_PREFIX=${WORKDIR}/git \
	  -DCMAKE_BUILD_TYPE=Debug \
	  -DCMAKE_INSTALL_PREFIX=${datadir}/kodi/addons \
          -DCMAKE_MODULE_PATH=${STAGING_DIR_HOST}${libdir}/kodi \
          -DCMAKE_PREFIX_PATH=${STAGING_DIR_HOST}${prefix} \
          -DPACKAGE_ZIP=1 \
        "

do_compile_prepend() {
	sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' \
	       -e 's:-pipe:${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS} -pipe:g' \
	          ${B}/CMakeFiles/*/flags.make
	sed -i -e 's:-pipe:${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS} -pipe:g'\
	          ${B}/CMakeFiles/*/link.txt
}

# Make zip package for manual installation
do_install_append() {
	install -d ${D}${datadir}/kodi/addons/packages/
	( cd ${D}${datadir}/kodi/addons
	  zip -r ${D}${datadir}/kodi/addons/packages/inputstream.adaptive-${PV}.zip inputstream.adaptive -x '*.debug*' )
}

# Doesn't get added automagically, dlopen()?
RDEPENDS_${PN} = "libkodiplatform"

INSANE_SKIP_${PN} = "dev-so"
FILES_${PN} += "${datadir}/kodi"
FILES_${PN}-dbg += "${datadir}/kodi/addons/*/.debug/"
