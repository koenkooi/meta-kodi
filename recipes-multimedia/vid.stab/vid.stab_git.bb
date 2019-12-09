SUMMARY = "Video stabilization library "
HOMEPAGE = "http://public.hronopik.de/vid.stab/"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4e572dadb7c9dcde312dfef274039145"

PV = "1.1.0"

SRCREV = "aeabc8daa7904f9edf7441a11f293965a5ef53b8"
SRC_URI = "git://github.com/georgmartius/vid.stab.git;protocol=https \
           file://0001-Cmake-stop-looking-at-proc-cpuinfo.patch \
          "

S = "${WORKDIR}/git"

inherit cmake lib_package
