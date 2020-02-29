SUMMARY = "NFS client library"
HOMEPAGE = "https://github.com/sahlberg/libnfs"
BUGTRACKER = "https://github.com/sahlberg/libnfs"

LICENSE = "LGPLv2.1 & GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=825301ba17efc9d188ee0abd4b924ada"

SRC_URI = "git://github.com/sahlberg/libnfs.git"
SRCREV = "${PN}-${PV}"
S = "${WORKDIR}/git"

inherit cmake
