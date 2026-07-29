SUMMARY = "Free portable AirPlay server implementation"
DESCRIPTION = "Apple AirPlay and AirTunes server library similar to \
ShairPort. Kodi links against it for its AirTunes audio streaming \
support (PACKAGECONFIG airtunes)."
HOMEPAGE = "https://github.com/juhovh/shairplay"

LICENSE = "MIT & LGPL-2.1-or-later & BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7fff59c88f17faa814f26f26b06a7100"

DEPENDS = "avahi libao"

PV = "0.9.0+git"
SRC_URI = "git://github.com/juhovh/shairplay.git;protocol=https;branch=master"
SRCREV = "096b61ad14c90169f438e690d096e3fcf87e504e"

CFLAGS:append = " -I${STAGING_INCDIR}/avahi-compat-libdns_sd"

inherit autotools pkgconfig
