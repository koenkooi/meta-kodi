SUMMARY = "Kodi's Tvheadend HTSP client addon"
HOMEPAGE = "https://github.com/kodi-pvr/pvr.hts"
BUGTRACKER = "https://github.com/kodi-pvr/pvr.hts/issues"

LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "8.2.4"

SRCREV = "${PV}-${KODICODENAME}"
SRC_URI = "git://github.com/kodi-pvr/pvr.hts.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "pvr.hts"
