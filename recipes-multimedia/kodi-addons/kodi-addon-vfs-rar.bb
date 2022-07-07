SUMMARY = "VFS RAR addon for Kodi."
HOMEPAGE = "https://github.com/xbmc/vfs.rar"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

DEPENDS += "kodi libtinyxml"

PV = "20.1.0"

SRCREV = "4e62b7541c422ec359d2c8bfa0d1b35fdacf89a2"
SRC_URI = "git://github.com/xbmc/vfs.rar.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "vfs.rar"
