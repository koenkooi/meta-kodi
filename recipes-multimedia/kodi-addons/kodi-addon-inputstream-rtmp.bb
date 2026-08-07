SUMMARY = "Kodi's RTMP inputstream addon"
HOMEPAGE = "https://github.com/xbmc/inputstream.rtmp"
BUGTRACKER = "https://github.com/xbmc/inputstream.rtmp/issues"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "22.1.2"

SRCREV = "228dbc36ae2666f6e43f023fa083bd3e612af7a2"
SRC_URI = "git://github.com/xbmc/inputstream.rtmp.git;nobranch=1;protocol=https"

KODIADDONNAME = "inputstream.rtmp"

# CMakeLists.txt find_package(RTMP REQUIRED), FindRTMP.cmake looks for
# librtmp, which is built by the rtmpdump recipe
DEPENDS += "rtmpdump"

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
    if [ -f "$manifest" ] && ! grep -q '>inputstream.rtmp<' "$manifest"; then
        sed -i 's#</addons>#  <addon optional="true">inputstream.rtmp</addon>\n</addons>#' "$manifest"
    fi
}
