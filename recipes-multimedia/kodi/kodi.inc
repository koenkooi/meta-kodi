# Some forked meta-kodi repositories using custom kodi urls, personally I think they should use variable instead of
# creating different recipes and diverse from upstream

inherit kodi-common

KODIVER ?= "20.0"

SRC_URI = "gitsm://github.com/xbmc/xbmc.git;protocol=https;nobranch=1"
SRCREV = "9bf56e0cbe7afd1ff29d5f4d7b68e8e301c7e5d5"
S = "${WORKDIR}/git"

PV = "${KODIVER}-${KODICODENAME}"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM ?= "file:///${WORKDIR}/git/LICENSE.md;md5=7b423f1c9388eae123332e372451a4f7"

