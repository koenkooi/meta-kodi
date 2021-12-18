SUMMARY = "Kodi inputstream addon for several manifest types"
HOMEPAGE = "https://github.com/peak3d/inputstream.adaptive"
BUGTRACKER = "https://github.com/peak3d/inputstream.adaptive/issues"

LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=930e2a5f63425d8dd72dbd7391c43c46"

inherit kodi-addon

DEPENDS += "expat"

PV = "20.0.3"

SRCREV = "785d2b10542f84417189409044b1165d47df23e2"
SRC_URI = "git://github.com/xbmc/inputstream.adaptive.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "inputstream.adaptive"

EXTRA_OECMAKE += "-DENABLE_INTERNAL_BENTO4=ON"

