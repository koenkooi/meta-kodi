inherit kodi-common

PACKAGECONFIG_append_class-target = " \
    ${@bb.utils.contains('VAAPISUPPORT', '1', 'vaapi', '', d)} \
    ${@bb.utils.contains('VDPAUSUPPORT', '1', 'vdpau', '', d)} \
"

PACKAGECONFIG[vaapi] = "-Dgallium-va=true, -Dgallium-va=false,libva"
PACKAGECONFIG[vdpau] = "-Dgallium-vdpau=true, -Dgallium-vdpau=false,libvdpau"

PACKAGES += "libvdpau-mesa libvdpau-mesa-dev"

FILES_libvdpau-mesa = "${libdir}/vdpau/*.so.*"
FILES_libvdpau-mesa-dev = "${libdir}/vdpau/*.so"

