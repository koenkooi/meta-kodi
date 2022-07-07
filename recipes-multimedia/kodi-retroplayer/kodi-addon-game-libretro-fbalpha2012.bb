SUMMARY = "Arcade (FB Alpha 2012)."
HOMEPAGE = "https://github.com/kodi-game/game.game.libretro.fbalpha2012"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://README.md;md5=1eca3064bd23ae0f31cc147de72d614d"

inherit kodi-addon

DEPENDS += "kodi fbalpha2012-libretro"

PV = "0.2.97.17-Nexus"

SRCREV = "7cd6ba0c1ee15507778b857105fdca003df030ab"
SRC_URI = "git://github.com/kodi-game/game.libretro.fbalpha2012.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "game.libretro.fbalpha2012"
