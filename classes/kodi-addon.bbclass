inherit kodi-common

KODI_ADDON_NAME ?= "${PN}"
KODI_ADDON_DIR ?= "${datadir}/kodi/addons"

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
  -DCMAKE_INSTALL_PREFIX=${KODI_ADDON_DIR} \
  -DCMAKE_MODULE_PATH='${STAGING_DIR_HOST}${libdir}/kodi;${STAGING_DIR_HOST}${datadir}/kodi/cmake' \
  -DCMAKE_PREFIX_PATH=${STAGING_DIR_HOST}${prefix} \
  -DKODI_INCLUDE_DIR=${STAGING_INCDIR}/kodi \
  -DPACKAGE_ZIP=1 \
"

# Make zip package for manual installation
do_install_append() {
	install -d ${D}${KODI_ADDON_DIR}/packages/
	( cd ${D}${KODI_ADDON_DIR}
	  zip -r ${D}${KODI_ADDON_DIR}/packages/${KODI_ADDON_NAME}-${PV}.zip ${KODI_ADDON_NAME} -x '*.debug*' )
}

# Doesn't get added automagically, dlopen()?
RDEPENDS_${PN} = "libkodiplatform"

INSANE_SKIP_${PN} = "dev-so libdir"
FILES_${PN} += "${KODI_ADDON_DIR}"
FILES_${PN}-dbg += "${KODI_ADDON_DIR}/*/.debug/"
