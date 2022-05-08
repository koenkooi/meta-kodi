SUMMARY = "videlan libudfread."
HOMEPAGE = "http://github.com/vlc-mirror/libudfread"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "git://code.videolan.org/videolan/libudfread.git;branch=master;protocol=https"

S = "${WORKDIR}/git"
SRCREV = "b3e6936a23f8af30a0be63d88f4695bdc0ea26e1"

inherit autotools pkgconfig

