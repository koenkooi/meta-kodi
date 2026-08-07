SUMMARY = "Kodi's HDHomeRun PVR client addon"
HOMEPAGE = "https://github.com/kodi-pvr/pvr.hdhomerun"
BUGTRACKER = "https://github.com/kodi-pvr/pvr.hdhomerun/issues"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "22.2.5"

SRCREV = "acd9911acd9be0f39b3f041669e8368e24edcdc4"
SRC_URI = "git://github.com/kodi-pvr/pvr.hdhomerun.git;nobranch=1;protocol=https"

KODIADDONNAME = "pvr.hdhomerun"

DEPENDS += "jsoncpp libhdhomerun"

# Auto-activate, see kodi-addon-pvr-hts.bb for the full rationale.
RDEPENDS:${PN} += "kodi"
pkg_postinst:${PN}() {
    manifest="$D${datadir}/kodi/system/addon-manifest.xml"
    if [ -f "$manifest" ] && ! grep -q '>pvr.hdhomerun<' "$manifest"; then
        sed -i 's#</addons>#  <addon optional="true">pvr.hdhomerun</addon>\n</addons>#' "$manifest"
    fi
}
