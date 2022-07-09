SUMMARY = "Commodore - C64 (VICE x64, fast)."
HOMEPAGE = "https://github.com/kodi-game/game.libretro.vice"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://README.md;md5=7733dedfe9fe84d3b6cfc2756cb457e2"

inherit kodi-addon

DEPENDS += "kodi vice-libretro"

PV = "3.5.0.16-Nexus"

SRCREV = "e65f5ba670c6251841cc1b90b3903d72b05b9ae9"
SRC_URI = "git://github.com/kodi-game/game.libretro.vice.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "game.libretro.vice"
