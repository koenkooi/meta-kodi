# for now kodi is not capable of running libretro cores that require opengl

SUMMARY = "Sega - Dreamcast/NAOMI (Flycast)."
HOMEPAGE = "https://github.com/kodi-game/game.libretro.flycast"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://README.md;md5=35a90a7856076ff2e93518a3bcab493f"

inherit kodi-addon

DEPENDS += "kodi flycast-libretro"

PV = "0.1.0.37-Nexus"

SRCREV = "3e1ae6b7c250073403696a1febb5b93c3823df7d"
SRC_URI = "git://github.com/kodi-game/game.libretro.flycast.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "game.libretro.flycast"
