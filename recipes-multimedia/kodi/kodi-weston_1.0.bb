SUMMARY = "Run Kodi as a Weston kiosk client"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "\
    file://weston-kodi.ini \
    file://kodi.conf \
"

S = "${UNPACKDIR}"

inherit allarch

# Weston autolaunches Kodi (as the weston user) with WAYLAND_DISPLAY set,
# so Kodi picks its wayland windowing at runtime.
do_install() {
    install -Dm644 ${S}/weston-kodi.ini ${D}${sysconfdir}/xdg/weston/weston-kodi.ini
    install -Dm644 ${S}/kodi.conf ${D}${systemd_system_unitdir}/weston.service.d/kodi.conf
}

FILES:${PN} += "${systemd_system_unitdir}"

RDEPENDS:${PN} = "kodi weston weston-init"
