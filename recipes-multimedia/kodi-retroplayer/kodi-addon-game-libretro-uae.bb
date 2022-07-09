SUMMARY = "Commodore - Amiga (PUAE)."
HOMEPAGE = "https://github.com/kodi-game/game.libretro.uae"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://README.md;md5=6021d74f73898a218fd62719fc5b19fe"

inherit kodi-addon

DEPENDS += "kodi puae-libretro"

PV = "2.6.1.28-Nexus"

SRCREV = "286db404f92ea09caf95ee672adfe7fbd86d0a18"
SRC_URI = "git://github.com/kodi-game/game.libretro.uae.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "game.libretro.uae"
