SUMMARY = "UDF filesystem reader library"
DESCRIPTION = "VideoLAN library for reading UDF filesystems, as used on \
DVD and Blu-ray discs and disc images. Kodi uses it for its optical media \
support."
HOMEPAGE = "https://code.videolan.org/videolan/libudfread"

LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "git://code.videolan.org/videolan/libudfread.git;protocol=https;branch=master"
# tag 1.2.0
SRCREV = "c3cd5cbb097924557ea4d9da1ff76a74620c51a8"

inherit autotools pkgconfig
