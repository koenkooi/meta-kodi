SUMMARY = "Kodi inputstream addon for several manifest types"
HOMEPAGE = "https://github.com/peak3d/inputstream.adaptive"
BUGTRACKER = "https://github.com/peak3d/inputstream.adaptive/issues"

LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=930e2a5f63425d8dd72dbd7391c43c46"

inherit kodi-addon

DEPENDS += "expat"

PV = "20.0.3"

SRCREV = "ea598cf9fab5961af507bdf87575d18e15d17b24"
SRC_URI = "git://github.com/xbmc/inputstream.adaptive.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "inputstream.adaptive"

EXTRA_OECMAKE += "-DENABLE_INTERNAL_BENTO4=ON"

