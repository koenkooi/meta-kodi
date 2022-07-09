# for now kodi is not capable of running libretro cores that require opengl

SUMMARY = "Nintendo - GameCube / Wii (Dolphin)."
HOMEPAGE = "https://github.com/kodi-game/game.libretro.dolphin"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://README.md;md5=1f264b709e8ad3ce64c50a9cdc697140"

inherit kodi-addon

DEPENDS += "kodi dolphin-libretro"

PV = "4.0.0.5-Nexus"

SRCREV = "3f8c7ebd807911e2b2ff66598ecf3e80e005d5c9"
SRC_URI = "git://github.com/kodi-game/game.libretro.dolphin.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "game.libretro.dolphin"
