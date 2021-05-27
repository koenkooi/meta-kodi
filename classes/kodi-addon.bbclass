inherit kodi-common

KODIADDONNAME ?= "${PN}"
KODIADDONDIR ?= "${datadir}/kodi/addons"

inherit cmake pkgconfig gettext

DEPENDS_append = " kodi-platform p8platform zip-native"
RDEPENDS_${PN}_append = " libkodiplatform"

EXTRA_OECMAKE_append = " \
  -DADDONS_TO_BUILD=${KODIADDONNAME} \
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

INSANE_SKIP_${PN} = "dev-so libdir"
FILES_${PN} += "${KODIADDONDIR}"
FILES_${PN}-dbg += "${KODIADDONDIR}/*/.debug/"
