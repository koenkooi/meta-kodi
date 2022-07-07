# for now kodi is not capable of running libretro cores that require opengl

SUMMARY = "Sony - PlayStation Portable (PPSSPP)."
HOMEPAGE = "https://github.com/kodi-game/game.libretro.ppsspp"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://README.md;md5=0b466087dd58cbf233ce618930f56065"

inherit kodi-addon

DEPENDS += "kodi ppsspp-libretro"

PV = "0.0.1.5-Nexus"

SRCREV = "78886ef08cb8624dbfa1c89f09b8cb0de5f1ebb5"
SRC_URI = "git://github.com/kodi-game/game.libretro.ppsspp.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "game.libretro.ppsspp"
