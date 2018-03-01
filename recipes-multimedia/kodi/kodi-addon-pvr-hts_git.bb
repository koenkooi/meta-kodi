SUMMARY = "Kodi Media Center PVR plugins"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://src/client.cpp;beginline=1;endline=19;md5=101ceb1255392ba347350e6c3e543f3a"

inherit kodi-addon

SRCREV_pvrhts = "095784b3190ee36abdc0001f267dbbd2db29ca2e"

SRCREV_FORMAT = "pvrhts"

PV = "4.2.13+gitr${SRCPV}"
SRC_URI = "git://github.com/kodi-pvr/pvr.hts.git;branch=master;destsuffix=pvr.hts;name=pvrhts \
          "

S = "${WORKDIR}/pvr.hts"

KODIADDONNAME = "pvr.hts"
