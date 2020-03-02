inherit kodi-common

KODIADDONNAME ?= "${PN}"
KODIADDONDIR ?= "${datadir}/kodi/addons"

DEPENDS += "zip-native \
            p8platform \
            kodi-platform \
          "

inherit cmake pkgconfig gettext

ASNEEDED = ""

EXTRA_OECMAKE = " \
  -DADDONS_TO_BUILD=inputstream.adaptive \
  -DADDON_SRC_PREFIX=${WORKDIR}/git \
  -DCMAKE_BUILD_TYPE=RelWithDebInfo \
  -DCMAKE_INSTALL_PREFIX=${KODIADDONDIR} \
  -DCMAKE_MODULE_PATH='${STAGING_DIR_HOST}${libdir}/kodi;${STAGING_DIR_HOST}${datadir}/kodi/cmake' \
  -DCMAKE_PREFIX_PATH=${STAGING_DIR_HOST}${prefix} \
  -DKODI_INCLUDE_DIR=${STAGING_INCDIR}/kodi \
  -DPACKAGE_ZIP=1 \
"

# Make zip package for manual installation
do_install_append() {
	install -d ${D}${KODIADDONDIR}/packages/
	( cd ${D}${KODIADDONDIR}
	  zip -r ${D}${KODIADDONDIR}/packages/${KODIADDONNAME}-${PV}.zip ${KODIADDONNAME} -x '*.debug*' )
}

# Doesn't get added automagically, dlopen()?
RDEPENDS_${PN} = "libkodiplatform"

INSANE_SKIP_${PN} = "dev-so libdir"
FILES_${PN} += "${KODIADDONDIR}"
FILES_${PN}-dbg += "${KODIADDONDIR}/*/.debug/"
