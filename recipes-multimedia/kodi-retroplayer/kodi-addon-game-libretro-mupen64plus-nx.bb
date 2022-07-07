# for now kodi is not capable of running libretro cores that require opengl

SUMMARY = "Nintendo - Nintendo 64 (Mupen64Plus-Next)."
HOMEPAGE = "https://github.com/kodi-game/game.libretro.mupen64plus-nx"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://README.md;md5=794bfc1db23575161c6a4dc468e167bb"

inherit kodi-addon

DEPENDS += "kodi mupen64plus-libretro"

PV = "2.2.0.18-Nexus"

SRCREV = "ac8d4e1a2caf4ddf622d8ced2fda907e3157f030"
SRC_URI = "git://github.com/kodi-game/game.libretro.mupen64plus-nx.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "game.libretro.mupen64plus-nx"
