SUMMARY = "visualization.goom addon for Kodi"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

DEPENDS += "glm"

inherit kodi-addon

PV = "20.1.0-Nexus"

SRCREV = "53f722562f6ee45a43c0229ada5a8cadad7bd37c"
SRC_URI = "git://github.com/xbmc/visualization.goom.git;protocol=https;nobranch=1"

S = "${WORKDIR}/git"

KODIADDONNAME = "visualization.goom"

