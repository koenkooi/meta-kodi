SUMMARY = "visualization.goom addon for Kodi"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

DEPENDS += "glm"

inherit kodi-addon

PV = "20.1.1-Nexus"

SRCREV = "9a9efffe6166048fe26a0598da700b8c3c5bf1f3"
SRC_URI = "git://github.com/xbmc/visualization.goom.git;protocol=https;nobranch=1"

S = "${WORKDIR}/git"

KODIADDONNAME = "visualization.goom"

PACKAGECONFIG[opengl] = ",,virtual/libgl"
PACKAGECONFIG[gles] = ",,virtual/libgles3"

PACKAGECONFIG ?= "${@bb.utils.filter('DISTRO_FEATURES', 'opengl gles', d)}"
