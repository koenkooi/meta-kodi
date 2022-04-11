SUMMARY = "Kodi inputstream addon for several manifest types"
HOMEPAGE = "https://github.com/peak3d/inputstream.adaptive"
BUGTRACKER = "https://github.com/peak3d/inputstream.adaptive/issues"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=cf64b7c0315e01b7fe438b0123ce16a0"

inherit kodi-addon

DEPENDS += "expat"

PV = "20.1.2"

SRCREV = "02d2faf5f770e072fe12987937c186ffdd71533a"
SRC_URI = "git://github.com/xbmc/inputstream.adaptive.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "inputstream.adaptive"

EXTRA_OECMAKE += "-DENABLE_INTERNAL_BENTO4=ON"

do_compile[network] = "1"
