SUMMARY = "Video stabilization library "
HOMEPAGE = "http://public.hronopik.de/vid.stab/"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4e572dadb7c9dcde312dfef274039145"

SRCREV = "v${PV}"
SRC_URI = "git://github.com/georgmartius/vid.stab.git;protocol=https;branch=master"
S = "${WORKDIR}/git"

do_patch() {
    sed -i '/FindSSE/d' ${S}/CMakeLists.txt
    sed -i '/FindSSE/d' ${S}/tests/CMakeLists.txt
}

inherit cmake lib_package
