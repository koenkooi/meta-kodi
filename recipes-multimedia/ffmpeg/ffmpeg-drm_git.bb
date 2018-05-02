SUMMARY = "101 code to validate ffmpeg DRM changes"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://main.c;beginline=2;endline=29;md5=274a60da08c9f2113c140a390abebff5"

DEPENDS = "ffmpeg zlib libdrm"

PV = "20180502"
SRC_URI = "git://github.com/BayLibre/ffmpeg-drm.git;protocol=https"
SRCREV = "fb839c4bb17e34b1470b758e2aac9e93d8113b3a"

S = "${WORKDIR}/git"

do_compile() {
	${CC} ${CFLAGS} ${LDFLAGS} -o ffmpeg-drm main.c -I${STAGING_INCDIR}/drm -lavcodec -lz -lm -lpthread -lavcodec -lavformat -lavutil -lswresample -ldrm
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ffmpeg-drm ${D}${bindir}
}
