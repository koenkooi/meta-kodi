SUMMARY = "Kodi's libarchive VFS addon (zip/7z/rar/tar/iso browsing)"
HOMEPAGE = "https://github.com/xbmc/vfs.libarchive"
BUGTRACKER = "https://github.com/xbmc/vfs.libarchive/issues"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "22.0.2"

SRCREV = "cc777554accfefa030bc7a9325a175c011f9ac90"
SRC_URI = "git://github.com/xbmc/vfs.libarchive.git;nobranch=1;protocol=https"

KODIADDONNAME = "vfs.libarchive"

# CMakeLists.txt find_package()s all of these directly, not just
# libarchive; LibLZMA is optional upstream but included here for feature
# parity with the addon's other builds.
DEPENDS += "libarchive bzip2 lz4 lzo xz zlib openssl"

# Auto-activate: see kodi-addon-pvr-hts.bb for the mechanism.
RDEPENDS:${PN} += "kodi"
pkg_postinst:${PN}() {
    manifest="$D${datadir}/kodi/system/addon-manifest.xml"
    if [ -f "$manifest" ] && ! grep -q '>vfs.libarchive<' "$manifest"; then
        sed -i 's#</addons>#  <addon optional="true">vfs.libarchive</addon>\n</addons>#' "$manifest"
    fi
}
