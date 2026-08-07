SUMMARY = "Kodi's HEIF image decoder addon"
HOMEPAGE = "https://github.com/xbmc/imagedecoder.heif"
BUGTRACKER = "https://github.com/xbmc/imagedecoder.heif/issues"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "22.0.2"

SRCREV = "3cbf9b6a0e57f6f9706cf7d7d8271d1ecf3c9693"
SRC_URI = "git://github.com/xbmc/imagedecoder.heif.git;nobranch=1;protocol=https"

KODIADDONNAME = "imagedecoder.heif"

# CMakeLists.txt also does find_package(de265 REQUIRED) and links
# DEPLIBS against de265_LIBRARIES directly (Findde265.cmake does its
# own pkg-config/find_library lookup) -- not just libheif.
DEPENDS += "libheif libde265"
