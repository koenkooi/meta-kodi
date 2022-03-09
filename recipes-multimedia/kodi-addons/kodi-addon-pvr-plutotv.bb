SUMMARY = "This is the Pluto.tv PVR client addon for Kodi."
HOMEPAGE = "https://github.com/kodi-pvr/pvr.plutotv"
BUGTRACKER = "https://github.com/kodi-pvr/pvr.plutotv/issues"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "20.1.0"

SRCREV = "367a531fe59a563068f78822f7e4ca64ab7e803a"
SRC_URI = "git://github.com/kodi-pvr/pvr.plutotv.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "pvr.plutotv"

