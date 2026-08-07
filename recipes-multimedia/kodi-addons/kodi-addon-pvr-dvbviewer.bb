SUMMARY = "Kodi's DVBViewer PVR client addon"
HOMEPAGE = "https://github.com/kodi-pvr/pvr.dvbviewer"
BUGTRACKER = "https://github.com/kodi-pvr/pvr.dvbviewer/issues"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "22.3.1"

SRCREV = "e8d1d81fc7966717b8cef250e70aaf708c8f1f8d"
SRC_URI = "git://github.com/kodi-pvr/pvr.dvbviewer.git;nobranch=1;protocol=https"

KODIADDONNAME = "pvr.dvbviewer"
