require recipes-graphics/xorg-driver/xorg-driver-video.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=4641deddaa80fe7ca88e944e1fd94a94"

SUMMARY = "X.Org X server -- nouveau video driver"

DESCRIPTION = "Open-source X.org graphics driver for NVIDIA graphics"

DEPENDS += "virtual/libx11 libxvmc drm xf86driproto glproto \
            virtual/libgl xineramaproto libpciaccess"

PV = "1.0.12+git${SRCPV}"

SRCREV = "6473b68762b0dca2dfccfdfc74100398b7459296"
SRC_URI = "git://anongit.freedesktop.org/git/nouveau/xf86-video-nouveau.git;protocol=https"

S = "${WORKDIR}/git"

RDEPENDS_${PN} += "xserver-xorg-module-exa"
