# Some forked meta-kodi repositories using custom kodi urls, personally I think they should use variable instead of
# creating different recipes and diverse from upstream

inherit kodi-common vpu

KODIVER ?= "19.3"

KODIURI ?= "gitsm://github.com/xbmc/xbmc.git;protocol=https;nobranch=1"
KODIWORKDIR ?= "${WORKDIR}/git"

KODIPATCHES ?= ""
KODIADDONS ?= ""

SRC_URI = "${KODIURI} ${KODIPATCHES} ${KODIADDONS}"
SRCREV = "${PV}"
S = "${KODIWORKDIR}"

PV = "${KODIVER}-Matrix"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM ?= "file:///${WORKDIR}/git/LICENSE.md;md5=7b423f1c9388eae123332e372451a4f7"
