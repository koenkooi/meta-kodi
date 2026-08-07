SUMMARY = "Kodi's DVBLink PVR client addon"
HOMEPAGE = "https://github.com/kodi-pvr/pvr.dvblink"
BUGTRACKER = "https://github.com/kodi-pvr/pvr.dvblink/issues"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "22.3.1"

SRCREV = "a9dd08f9f3e092a1a4d6d6a2e6b49f1f0b6092ef"
SRC_URI = "git://github.com/kodi-pvr/pvr.dvblink.git;nobranch=1;protocol=https"

KODIADDONNAME = "pvr.dvblink"
