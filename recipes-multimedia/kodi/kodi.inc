# Some forked meta-kodi repositories using custom kodi urls, personally I think they should use variable instead of
# creating different recipes and diverse from upstream

inherit kodi-common vpu

KODIVER ?= "18.9"

KODIURI ?= "gitsm://github.com/xbmc/xbmc.git;protocol=https;branch=${KODICODENAME}"
KODIREV ?= "${KODIVER}-${KODICODENAME}"
KODIWORKDIR ?= "${WORKDIR}/git"

KODIPATCHES ?= ""
KODIADDONS ?= ""

SRC_URI = "${KODIURI} ${KODIPATCHES} ${KODIADDONS}"
SRCREV = "${KODIREV}"
S = "${KODIWORKDIR}"

PV = "${KODIVER}+git${SRCPV}"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM ?= "file:///${WORKDIR}/git/LICENSE.md;md5=7b423f1c9388eae123332e372451a4f7"
