SUMMARY = "Kodi's adaptive bitrate inputstream addon (MPEG-DASH, HLS, Smooth Streaming)"
HOMEPAGE = "https://github.com/xbmc/inputstream.adaptive"
BUGTRACKER = "https://github.com/xbmc/inputstream.adaptive/issues"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSES/GPL-2.0-or-later;md5=c387a7644efc4ca4879485ed792dbc51"

inherit kodi-addon

PV = "22.3.19"

SRCREV = "0801282232050a8d9d67269ada71b88f795c6889"
SRC_URI = "git://github.com/xbmc/inputstream.adaptive.git;nobranch=1;protocol=https"

KODIADDONNAME = "inputstream.adaptive"

DEPENDS += "bento4 nlohmann-json nss pugixml"

# aarch64 CDM loader shim: built by lib/cdm_aarch64 (guarded on
# CMAKE_SYSTEM_PROCESSOR MATCHES aarch64|arm64) but has no install()
# rule upstream -- LibreELEC's package.mk copies it out of the build
# tree the same way.
do_install:append:aarch64() {
    install -m 0755 ${B}/lib/cdm_aarch64/libcdm_aarch64_loader.so ${D}${KODIADDONDIR}/${KODIADDONNAME}/
}
