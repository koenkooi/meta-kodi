SUMMARY = "Nintendo - DS (DeSmuME)."
HOMEPAGE = "https://github.com/kodi-game/game.libretro.desmume"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://README.md;md5=8b568c675fa6f210d6abc58f2dc0187d"

inherit kodi-addon

DEPENDS += "kodi desmume-libretro"

PV = "0.0.1.15-Nexus"

SRCREV = "c82d356a41d37e8b4b5adfc1c3e69f72e65ca8e9"
SRC_URI = "git://github.com/kodi-game/game.libretro.desmume.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "game.libretro.desmume"
