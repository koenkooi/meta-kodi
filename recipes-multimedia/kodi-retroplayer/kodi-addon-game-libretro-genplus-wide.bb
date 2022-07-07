SUMMARY = "Sega - MS/GG/MD/CD (Genesis Plus GX Wide)."
HOMEPAGE = "https://github.com/kodi-game/game.libretro.genplus-wide"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://README.md;md5=83a4a6e6af67af92e1a44faa7b50be7f"

inherit kodi-addon

DEPENDS += "kodi genesis-plus-gx-wide-libretro"

PV = "1.7.4.4-Nexus"

SRCREV = "6ac8df1d1dee7ab8f3cb7673539b5f046fad6ae1"
SRC_URI = "git://github.com/kodi-game/game.libretro.genplus-wide.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "game.libretro.genplus-wide"
