SUMMARY = "Free portable AirPlay server implementation similar to ShairPort."
HOMEPAGE = "http://github.com/juhovh/shairplay"
LICENSE = "MIT & LGPL-2.1-or-later & BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7fff59c88f17faa814f26f26b06a7100"

SRC_URI = "git://github.com/juhovh/shairplay.git;branch=master;protocol=https"

S = "${WORKDIR}/git"
SRCREV = "096b61ad14c90169f438e690d096e3fcf87e504e"
inherit autotools pkgconfig

DEPENDS = "libao avahi"

CFLAGS:append = " -I${STAGING_INCDIR}/avahi-compat-libdns_sd"

