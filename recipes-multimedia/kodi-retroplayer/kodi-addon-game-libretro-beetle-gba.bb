SUMMARY = "Nintendo - Game Boy Advance (Beetle GBA)."
HOMEPAGE = "https://github.com/kodi-game/game.libretro.beetle-gba"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://README.md;md5=58e8e35d861f27c4d46669e127a6ecdc"

inherit kodi-addon

DEPENDS += "kodi beetle-gba-libretro"

PV = "0.9.36.16-Nexus"

SRCREV = "79e39134ff74b8f5f923e886a02c630045f2bef7"
SRC_URI = "git://github.com/kodi-game/game.libretro.beetle-gba.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "game.libretro.beetle-gba"
