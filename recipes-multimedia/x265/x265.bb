SUMMARY = "x265 HEVC Encoder"
HOMEPAGE = "https://bitbucket.org/multicoreware/x265/wiki/Home"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../COPYING;md5=c9e0427bc58f129f99728c62d4ad4091"

DEPENDS:append:x86:class-target =    " nasm-native"
DEPENDS:append:x86-64:class-target = " nasm-native"

PV = "3.4"

SRCREV = "07295ba7ab551bb9c1580fdaee3200f1b45711b7"
SRC_URI = "git://github.com/videolan/x265.git;protocol=https;nobranch=1"
S = "${WORKDIR}/git/source"

inherit cmake lib_package

EXTRA_OECMAKE = "-DENABLE_PIC=ON"
