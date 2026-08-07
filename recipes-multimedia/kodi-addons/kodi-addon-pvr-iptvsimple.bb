SUMMARY = "Kodi's IPTV Simple Client PVR addon"
HOMEPAGE = "https://github.com/kodi-pvr/pvr.iptvsimple"
BUGTRACKER = "https://github.com/kodi-pvr/pvr.iptvsimple/issues"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "22.6.4"

SRCREV = "c5ec2eb307a5bb1fa8033ebde42b90407f132f00"
SRC_URI = "git://github.com/kodi-pvr/pvr.iptvsimple.git;nobranch=1;protocol=https"

KODIADDONNAME = "pvr.iptvsimple"

# CMakeLists.txt find_package()s pugixml, ZLIB and lzma (xz) directly
DEPENDS += "pugixml zlib xz"

# Auto-activate: CAddonMgr reads kodi's own addon-manifest.xml at every
# startup and enables any listed addon ID once its addon.xml is found on
# disk (this recipe installs straight into ${datadir}/kodi/addons via
# kodi-addon.bbclass, no repository-fetch flow). "optional" auto-enables
# once without forcing a hard boot failure if the addon is ever missing.
# Runs as a postinst on this package (not a kodi do_install:append) so
# activation travels with the addon that needs it, not the base kodi
# recipe -- $D covers both the rootfs-time and deferred-first-boot cases.
RDEPENDS:${PN} += "kodi"
pkg_postinst:${PN}() {
    manifest="$D${datadir}/kodi/system/addon-manifest.xml"
    if [ -f "$manifest" ] && ! grep -q '>pvr.iptvsimple<' "$manifest"; then
        sed -i 's#</addons>#  <addon optional="true">pvr.iptvsimple</addon>\n</addons>#' "$manifest"
    fi
}
