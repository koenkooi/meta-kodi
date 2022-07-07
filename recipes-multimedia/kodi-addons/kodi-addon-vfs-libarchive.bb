SUMMARY = "VFS libarchive addon for Kodi."
HOMEPAGE = "https://github.com/xbmc/vfs.libarchive"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

DEPENDS += "kodi libarchive zlib openssl lzo bzip2 xz lz4"

PV = "20.2.0"

SRCREV = "db4e98a5055156b68167ccd5d7c9e1eb0c92844b"
SRC_URI = "git://github.com/xbmc/vfs.libarchive.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "vfs.libarchive"
