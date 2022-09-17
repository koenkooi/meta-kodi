# Some forked meta-kodi repositories using custom kodi urls, personally I think they should use variable instead of
# creating different recipes and diverse from upstream

inherit kodi-common

KODIVER ?= "20.0a3-Nexus"

SRC_URI = "git://github.com/xbmc/xbmc.git;protocol=https;branch=master"
SRCREV = "3a6a5fd553bb4a0b81371f67581effa42b1663ba"
S = "${WORKDIR}/git"

PV = "${KODIVER}-${KODICODENAME}"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM ?= "file:///${WORKDIR}/git/LICENSE.md;md5=7b423f1c9388eae123332e372451a4f7"

