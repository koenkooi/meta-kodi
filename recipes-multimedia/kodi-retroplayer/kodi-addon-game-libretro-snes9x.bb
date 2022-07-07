SUMMARY = "Nintendo - SNES / SFC (Snes9x - Current)."
HOMEPAGE = "https://github.com/kodi-game/game.libretro.snes9x"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://README.md;md5=d64a37cccad3e9864a75520368d78f5a"

inherit kodi-addon

DEPENDS += "kodi snes9x-libretro"

PV = "1.60.0.24-Nexus"

SRCREV = "1a1e1d3136b061f6548086d34c472212213e7eaa"
SRC_URI = "git://github.com/kodi-game/game.libretro.snes9x.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "game.libretro.snes9x"
