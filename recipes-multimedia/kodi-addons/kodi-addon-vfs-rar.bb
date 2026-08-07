SUMMARY = "Kodi's RAR VFS addon (browsing .rar archives)"
HOMEPAGE = "https://github.com/xbmc/vfs.rar"
BUGTRACKER = "https://github.com/xbmc/vfs.rar/issues"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "22.0.4"

SRCREV = "fdab4e5475a1038ae352e249c21e6b0a2540bae0"
SRC_URI = "git://github.com/xbmc/vfs.rar.git;nobranch=1;protocol=https"

KODIADDONNAME = "vfs.rar"

# CMakeLists.txt only find_package()s TinyXML (already staged via
# kodi-addon.bbclass -> kodi-platform -> libtinyxml). Unrar itself is
# bundled in-tree under lib/UnrarXLib, so no extra DEPENDS needed.
