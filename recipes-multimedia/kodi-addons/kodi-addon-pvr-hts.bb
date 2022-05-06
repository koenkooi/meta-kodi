SUMMARY = "Kodi's Tvheadend HTSP client addon"
HOMEPAGE = "https://github.com/kodi-pvr/pvr.hts"
BUGTRACKER = "https://github.com/kodi-pvr/pvr.hts/issues"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "20.3.0"

SRCREV = "62db48de3710446c4be4e207565747630408a245"
SRC_URI = "git://github.com/kodi-pvr/pvr.hts.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "pvr.hts"
