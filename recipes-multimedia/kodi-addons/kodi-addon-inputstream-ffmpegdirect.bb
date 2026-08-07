SUMMARY = "Kodi inputstream addon for FFmpeg-openable streams (plain TS, HLS, DASH without DRM)"
HOMEPAGE = "https://github.com/xbmc/inputstream.ffmpegdirect"
BUGTRACKER = "https://github.com/xbmc/inputstream.ffmpegdirect/issues"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

inherit kodi-addon

PV = "22.2.6"

SRCREV = "557e9ad2549d747cb5fc7930538f8dba9d2e9842"
SRC_URI = "git://github.com/xbmc/inputstream.ffmpegdirect.git;nobranch=1;protocol=https"

KODIADDONNAME = "inputstream.ffmpegdirect"

# CMakeLists.txt only find_package()s Kodi, FFMPEG, ZLIB and BZip2 on
# Linux (LibXml2/Iconv/OpenSSL are WIN32-only). zvbi/gmp/libpng are not
# referenced at all -- those are ffmpeg's own optional build deps, not
# this addon's, so no DEPENDS on the new zvbi recipe here.
DEPENDS += "ffmpeg zlib bzip2"

# Auto-activate, see kodi-addon-pvr-hts.bb for the full rationale.
RDEPENDS:${PN} += "kodi"
pkg_postinst:${PN}() {
    manifest="$D${datadir}/kodi/system/addon-manifest.xml"
    if [ -f "$manifest" ] && ! grep -q '>inputstream.ffmpegdirect<' "$manifest"; then
        sed -i 's#</addons>#  <addon optional="true">inputstream.ffmpegdirect</addon>\n</addons>#' "$manifest"
    fi
}
