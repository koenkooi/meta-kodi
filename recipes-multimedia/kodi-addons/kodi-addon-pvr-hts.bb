SUMMARY = "Kodi's Tvheadend HTSP client addon"
HOMEPAGE = "https://github.com/kodi-pvr/pvr.hts"
BUGTRACKER = "https://github.com/kodi-pvr/pvr.hts/issues"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "22.9.1"

SRCREV = "ae5fb01d74e4003b35935490dd8474f9de8589ad"
SRC_URI = "git://github.com/kodi-pvr/pvr.hts.git;nobranch=1;protocol=https"

KODIADDONNAME = "pvr.hts"

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
    if [ -f "$manifest" ] && ! grep -q '>pvr.hts<' "$manifest"; then
        sed -i 's#</addons>#  <addon optional="true">pvr.hts</addon>\n</addons>#' "$manifest"
    fi
}
