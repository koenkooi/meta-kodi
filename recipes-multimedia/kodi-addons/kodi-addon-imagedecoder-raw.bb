SUMMARY = "Kodi's RAW image decoder addon"
HOMEPAGE = "https://github.com/xbmc/imagedecoder.raw"
BUGTRACKER = "https://github.com/xbmc/imagedecoder.raw/issues"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "22.0.4"

SRCREV = "76c9d32555a35ca553d315e1dadbb5a91f36407c"
SRC_URI = "git://github.com/xbmc/imagedecoder.raw.git;nobranch=1;protocol=https"

KODIADDONNAME = "imagedecoder.raw"

# CMakeLists.txt: find_package(RAW REQUIRED), DEPLIBS links
# RAW_LIBRARIES. FindRAW.cmake only pulls in jpeg/lcms2/zlib when
# libraw is a static .a; OE's libraw builds shared, so that branch
# never triggers.
DEPENDS += "libraw"
