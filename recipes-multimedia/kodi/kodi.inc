# Some forked meta-kodi repositories using custom kodi urls, personally I think they should use variable instead of
# creating different recipes and diverse from upstream

inherit kodi-common

KODIVER ?= "20.0"

SRC_URI = "gitsm://github.com/xbmc/xbmc.git;protocol=https;nobranch=1"
SRCREV = "bda5d8a49ab36545287e0cf17093b734b3ddfb9a"
S = "${WORKDIR}/git"

PV = "${KODIVER}-${KODICODENAME}"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM ?= "file:///${WORKDIR}/git/LICENSE.md;md5=7b423f1c9388eae123332e372451a4f7"

