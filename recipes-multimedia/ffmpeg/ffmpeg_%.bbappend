inherit vpu

PACKAGECONFIG[vidstab] = "--enable-libvidstab,--disable-libvidstab,vid.stab"
PACKAGECONFIG[x265] = "--enable-libx265,--disable-libx265,x265"

KODIFFMPEGADDITIONALS ?= " \
    ${@bb.utils.contains('VAAPISUPPORT', '1', 'vaapi', '', d)} \
    ${@bb.utils.contains('VDPAUSUPPORT', '1', 'vdpau', '', d)} \
    vidstab \
    x265 \
"

PACKAGECONFIG:append = " ${KODIFFMPEGADDITIONALS}"
