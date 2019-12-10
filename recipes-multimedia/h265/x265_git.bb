SUMMARY = "x265 HEVC Encoder"
HOMEPAGE = "https://bitbucket.org/multicoreware/x265/wiki/Home"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../COPYING;md5=c9e0427bc58f129f99728c62d4ad4091"

DEPENDS_append_x86_class-target =    " nasm-native"
DEPENDS_append_x86-64_class-target = " nasm-native"

PV = "3.1.2+git"

SRCREV = "8c2a1669525f367b8b73e3378beb2b0d66cd60cb"
SRC_URI = "git://github.com/videolan/x265.git;protocol=https"

S = "${WORKDIR}/git/source/"

inherit cmake lib_package

EXTRA_OECMAKE = "-DENABLE_PIC=ON"
