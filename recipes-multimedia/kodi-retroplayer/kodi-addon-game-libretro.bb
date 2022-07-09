SUMMARY = "Libretro wrapper for Kodi's Game API."
HOMEPAGE = "https://github.com/kodi-game/game.libretro"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

DEPENDS += "kodi libtinyxml libretro-common rcheevos"

PV = "20.2.0"

SRCREV = "f172bd2287ff16ad7113293316606bdd2774996c"
SRC_URI = "git://github.com/kodi-game/game.libretro.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "game.libretro"
