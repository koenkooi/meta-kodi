SUMMARY = "This is the iptvsimple PVR client addon for Kodi."
HOMEPAGE = "https://github.com/kodi-pvr/pvr.iptvsimple"
BUGTRACKER = "https://github.com/kodi-pvr/pvr.iptvsimple/issues"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

DEPENDS = "pugixml"

inherit kodi-addon

PV = "20.3.0"

SRCREV = "f050a34b1a9b25c807a239f549a5654880fa5af8"
SRC_URI = "git://github.com/kodi-pvr/pvr.iptvsimple.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "pvr.iptvsimple"

