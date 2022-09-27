SUMMARY = "Video stabilization library "
HOMEPAGE = "http://public.hronopik.de/vid.stab/"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4e572dadb7c9dcde312dfef274039145"

SRCREV = "90c76aca2cb06c3ff6f7476a7cd6851b39436656"
SRC_URI = "git://github.com/georgmartius/vid.stab.git;protocol=https;branch=master"
S = "${WORKDIR}/git"

DEPENDS = "${@bb.utils.contains('TOOLCHAIN', 'clang', 'openmp', '', d)}"

inherit cmake lib_package
