SUMMARY = "Kodi TexturePacker build tool"
DESCRIPTION = "Packs Kodi skin textures into .xbt archives at build time. \
Built from the Kodi source tree and passed to the main kodi recipe via \
-DWITH_TEXTUREPACKER."
HOMEPAGE = "https://kodi.tv/"

require kodi-source.inc

DEPENDS += "giflib-native libjpeg-turbo-native libpng-native lzo-native zlib-native"

inherit cmake
inherit_defer native

OECMAKE_SOURCEPATH = "${S}/tools/depends/native/TexturePacker/src"

# Standalone TexturePacker builds never get Kodi's main CMake's
# SYSTEM_DEFINES, so TARGET_POSIX (guards windows.h in cmdlineargs.h)
# has to be passed explicitly.
EXTRA_OECMAKE = "-DKODI_SOURCE_DIR=${S} -DSYSTEM_DEFINES=TARGET_POSIX"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/TexturePacker ${D}${bindir}/TexturePacker
}
