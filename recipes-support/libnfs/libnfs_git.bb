SUMMARY = "NFS client library"

LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=0019ace2726c6f181791a9ac04c7ac6a"

PV = "2.0.0+git"
SRCREV = "54405d994ef9275fe7552f43c12bf2d39c0d9ec9"
SRC_URI = "git://github.com/sahlberg/libnfs.git;protocol=https"

S = "${WORKDIR}/git"

inherit cmake

