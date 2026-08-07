SUMMARY = "Kodi's RTMP inputstream addon"
HOMEPAGE = "https://github.com/xbmc/inputstream.rtmp"
BUGTRACKER = "https://github.com/xbmc/inputstream.rtmp/issues"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "22.1.2"

SRCREV = "228dbc36ae2666f6e43f023fa083bd3e612af7a2"
SRC_URI = "git://github.com/xbmc/inputstream.rtmp.git;nobranch=1;protocol=https"

KODIADDONNAME = "inputstream.rtmp"

# CMakeLists.txt find_package(RTMP REQUIRED), FindRTMP.cmake looks for
# librtmp, which is built by the rtmpdump recipe
DEPENDS += "rtmpdump"
