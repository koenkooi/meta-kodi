SUMMARY = "Kodi joystick support (drivers and button maps)"
HOMEPAGE = "https://github.com/xbmc/peripheral.joystick"
BUGTRACKER = "https://github.com/xbmc/peripheral.joystick/issues"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

DEPENDS = "libtinyxml2 udev"

PV = "20.1.5"
SRCREV = "963d2df4cdbf612d8fe820de5410b9544f8595fb"
SRC_URI = "git://github.com/xbmc/peripheral.joystick.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "peripheral.joystick"
