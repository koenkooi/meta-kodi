SUMMARY = "dav1d AV1 decoder"
DESCRIPTION = "Targeted to be small, portable and fast."
HOMEPAGE = "https://code.videolan.org/videolan/dav1d"
SECTION = "multimedia"
LICENSE = "BSD-2-Clause"

LIC_FILES_CHKSUM = "file://COPYING;md5=c8055cfe7548dfdaa3a6dc45d8793669"

SRC_URI = "git://code.videolan.org/videolan/dav1d.git;protocol=https;branch=master"
PV = "1.0.0"
SRCREV = "99172b11470776177939c3d2bc366fe8d904eab7"

S = "${WORKDIR}/git"

DEPENDS:x86 = "nasm-native"
DEPENDS:x86-64 = "nasm-native"

inherit meson

