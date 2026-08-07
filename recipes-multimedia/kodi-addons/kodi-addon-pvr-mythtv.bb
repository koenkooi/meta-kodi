SUMMARY = "Kodi's MythTV PVR client addon"
HOMEPAGE = "https://github.com/janbar/pvr.mythtv"
BUGTRACKER = "https://github.com/janbar/pvr.mythtv/issues"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "22.3.6"

# lib/cppmyth finds and links zlib directly, not covered by kodi's addon SDK
DEPENDS = "zlib"

SRCREV = "127ae9e90f0d8badc6205c1fa56cc0b712b93c2d"
SRC_URI = "git://github.com/janbar/pvr.mythtv.git;nobranch=1;protocol=https"

KODIADDONNAME = "pvr.mythtv"
