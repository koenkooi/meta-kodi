SUMMARY = "Kodi joystick support (drivers and button maps)"
HOMEPAGE = "https://github.com/xbmc/peripheral.joystick"
BUGTRACKER = "https://github.com/xbmc/peripheral.joystick/issues"

LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

DEPENDS = "libtinyxml2 udev"

PV = "20.1.0"
SRCREV = "f19f6f850fb020d4448ab07bbc4b834ef99ede3a"
SRC_URI = "git://github.com/xbmc/peripheral.joystick.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "peripheral.joystick"
