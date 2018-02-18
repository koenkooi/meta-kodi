ACCEL ?= ""
ACCEL_x86 = "vaapi vdpau"
ACCEL_x86-64 = "vaapi vdpau"

PACKAGECONFIG = "avdevice avfilter avcodec avformat swresample swscale postproc \
                 bzlib gpl lzma theora x264 \
                 ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'xv', '', d)} \
                 ${ACCEL}"
