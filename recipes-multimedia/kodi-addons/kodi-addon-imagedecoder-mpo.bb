SUMMARY = "Kodi's MPO image decoder addon"
HOMEPAGE = "https://github.com/xbmc/imagedecoder.mpo"
BUGTRACKER = "https://github.com/xbmc/imagedecoder.mpo/issues"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "22.0.3"

SRCREV = "ae9eb0e1fee1b9b5db47f4be8c4b155d15847985"
SRC_URI = "git://github.com/xbmc/imagedecoder.mpo.git;nobranch=1;protocol=https"

KODIADDONNAME = "imagedecoder.mpo"

# CMakeLists.txt: find_package(JPEG REQUIRED), DEPLIBS links
# JPEG_LIBRARIES directly. kodi itself depends on libjpeg-turbo via
# ffmpeg, but that is not this addon's own sysroot -- link it here too.
DEPENDS += "libjpeg-turbo"
