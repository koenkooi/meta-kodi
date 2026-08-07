SUMMARY = "Kodi's IPTV Simple Client PVR addon"
HOMEPAGE = "https://github.com/kodi-pvr/pvr.iptvsimple"
BUGTRACKER = "https://github.com/kodi-pvr/pvr.iptvsimple/issues"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "22.6.4"

SRCREV = "c5ec2eb307a5bb1fa8033ebde42b90407f132f00"
SRC_URI = "git://github.com/kodi-pvr/pvr.iptvsimple.git;nobranch=1;protocol=https"

KODIADDONNAME = "pvr.iptvsimple"

# CMakeLists.txt find_package()s pugixml, ZLIB and lzma (xz) directly
DEPENDS += "pugixml zlib xz"
