SUMMARY = "Kodi inputstream addon for several manifest types"
HOMEPAGE = "https://github.com/peak3d/inputstream.adaptive"
BUGTRACKER = "https://github.com/peak3d/inputstream.adaptive/issues"

LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=930e2a5f63425d8dd72dbd7391c43c46"

inherit kodi-addon

DEPENDS += "expat"

PV = "2.6.18"

SRCREV = "${PV}-${KODICODENAME}"
SRC_URI = "git://github.com/peak3d/inputstream.adaptive.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "inputstream.adaptive"
