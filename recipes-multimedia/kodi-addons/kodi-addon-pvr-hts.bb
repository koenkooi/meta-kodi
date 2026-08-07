SUMMARY = "Kodi's Tvheadend HTSP client addon"
HOMEPAGE = "https://github.com/kodi-pvr/pvr.hts"
BUGTRACKER = "https://github.com/kodi-pvr/pvr.hts/issues"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "22.9.1"

SRCREV = "ae5fb01d74e4003b35935490dd8474f9de8589ad"
SRC_URI = "git://github.com/kodi-pvr/pvr.hts.git;nobranch=1;protocol=https"

KODIADDONNAME = "pvr.hts"
