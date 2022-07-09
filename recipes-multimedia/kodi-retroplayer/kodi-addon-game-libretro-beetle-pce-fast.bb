SUMMARY = "NEC - PC Engine / CD (Beetle PCE FAST)."
HOMEPAGE = "https://github.com/kodi-game/game.libretro.beetle-pce-fast"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://README.md;md5=c819f0f0b6a9e221e09bb37bebcaa9c5"

inherit kodi-addon

DEPENDS += "kodi beetle-pce-fast-libretro"

PV = "0.9.38.24-Nexus"

SRCREV = "2d7785eddcaa1aad5b9ae702af9c3c4fd3e9e666"
SRC_URI = "git://github.com/kodi-game/game.libretro.beetle-pce-fast.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "game.libretro.beetle-pce-fast"
