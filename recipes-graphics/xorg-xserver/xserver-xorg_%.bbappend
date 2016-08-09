PACKAGECONFIG = "dri3 xshmfence systemd-logind dri2 udev ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'dri glx', '', d)} \
                   ${@bb.utils.contains("DISTRO_FEATURES", "wayland", "xwayland", "", d)} \
"

