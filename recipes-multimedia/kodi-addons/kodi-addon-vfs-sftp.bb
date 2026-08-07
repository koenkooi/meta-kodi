SUMMARY = "Kodi's SFTP VFS addon"
HOMEPAGE = "https://github.com/xbmc/vfs.sftp"
BUGTRACKER = "https://github.com/xbmc/vfs.sftp/issues"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "22.0.2"

SRCREV = "33a45ba21c0e6dd489429d742a7abe0376cec4ea"
SRC_URI = "git://github.com/xbmc/vfs.sftp.git;nobranch=1;protocol=https"

KODIADDONNAME = "vfs.sftp"

# CMakeLists.txt find_package(SSH REQUIRED)s libssh directly (its bundled
# FindSSH.cmake pkg_check_modules()s "libssh", not libssh2). OpenSSL and
# zlib are also find_package()d but already staged via kodi/kodi-platform.
DEPENDS += "libssh"
