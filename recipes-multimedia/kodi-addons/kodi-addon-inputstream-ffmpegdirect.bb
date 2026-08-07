SUMMARY = "Kodi inputstream addon for FFmpeg-openable streams (plain TS, HLS, DASH without DRM)"
HOMEPAGE = "https://github.com/xbmc/inputstream.ffmpegdirect"
BUGTRACKER = "https://github.com/xbmc/inputstream.ffmpegdirect/issues"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "22.2.6"

SRCREV = "557e9ad2549d747cb5fc7930538f8dba9d2e9842"
SRC_URI = "git://github.com/xbmc/inputstream.ffmpegdirect.git;nobranch=1;protocol=https"

KODIADDONNAME = "inputstream.ffmpegdirect"

# CMakeLists.txt only find_package()s Kodi, FFMPEG, ZLIB and BZip2 on
# Linux (LibXml2/Iconv/OpenSSL are WIN32-only). zvbi/gmp/libpng are not
# referenced at all -- those are ffmpeg's own optional build deps, not
# this addon's, so no DEPENDS on the new zvbi recipe here.
DEPENDS += "ffmpeg zlib bzip2"
