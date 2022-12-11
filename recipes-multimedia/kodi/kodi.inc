# Some forked meta-kodi repositories using custom kodi urls, personally I think they should use variable instead of
# creating different recipes and diverse from upstream

inherit kodi-common

KODIVER ?= "20.0-rc1"

SRC_URI = "git://github.com/xbmc/xbmc.git;protocol=https;branch=master"
SRCREV = "911ad268d07049f6dc08fd2bbd7feae60c6fb6e8"
S = "${WORKDIR}/git"

PV = "${KODIVER}-${KODICODENAME}"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM ?= "file:///${WORKDIR}/git/LICENSE.md;md5=7b423f1c9388eae123332e372451a4f7"

