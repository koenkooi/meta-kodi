# Some forked meta-kodi repositories using custom kodi urls, personally I think they should use variable instead of
# creating different recipes and diverse from upstream

inherit kodi-common

KODI_URI ?= "gitsm://github.com/xbmc/xbmc.git;protocol=https;branch=${KODI_CODENAME}"
KODI_REV ?= "${PV}-${KODI_CODENAME}"
KODI_WORKDIR ?= "${WORKDIR}/git"

KODI_PATCHES ?= ""
KODI_ADDONS ?= ""

SRC_URI = "${KODI_URI} ${KODI_PATCHES} ${KODI_ADDONS}"
SRCREV = "${KODI_REV}"
S = "${KODI_WORKDIR}"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM ?= "file:///${WORKDIR}/git/LICENSE.md;md5=7b423f1c9388eae123332e372451a4f7"