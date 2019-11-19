ACCEL ?= ""
ACCEL_x86 = "vaapi vdpau"
ACCEL_x86-64 = "vaapi vdpau"

DEPENDS += "vid.stab x265"

EXTRA_OECONF += "--enable-libvidstab \
                 --enable-libx265 \
                "

PACKAGECONFIG = "avdevice avfilter avcodec avformat swresample swscale postproc \
                 bzlib gpl lzma theora x264 \
                 ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'xv', '', d)} \
                 ${ACCEL}"
