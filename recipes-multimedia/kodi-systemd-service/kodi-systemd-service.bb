SUMMARY = "Kodi Media Center systemd service"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit features_check kodi-common systemd

PREFFERED_KODI_SERVICE ?= "kodi-${@bb.utils.contains('KODIGRAPHICALBACKEND', 'x11', 'x11', 'gbm', d)}.service"

SRC_URI = "file://${PREFFERED_KODI_SERVICE}"

REQUIRED_DISTRO_FEATURES = "systemd"

SYSTEMD_SERVICE:${PN} = "${PREFFERED_KODI_SERVICE}"
SYSTEMD_AUTO_ENABLE:${PN} = "${KODISYSTEMDAUTOSTART}"

FILES:${PN} = "${systemd_unitdir}/system"

do_install() {
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${WORKDIR}/${PREFFERED_KODI_SERVICE} ${D}${systemd_unitdir}/system/
}
