SUMMARY = "Wayland C++ bindings"
HOMEPAGE = "https://nilsbrause.github.io/waylandpp_docs"
BUGTRACKER = "https://github.com/NilsBrause/waylandpp/issues"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7f6b13e4480850c59e176edd427d996e"

SRCREV = "${PV}"
SRC_URI = "git://github.com/NilsBrause/waylandpp.git;protocol=https"
S = "${WORKDIR}/git"

inherit cmake lib_package native pkgconfig

DEPENDS += "doxygen-native wayland pugixml"

DEPENDS_class-native += "wayland-native pugixml-native"
DEPENDS_class-target += "waylandpp-native"

BBCLASSEXTEND = "native nativesdk"
