VAAPISUPPORT ?= "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', '1', '0', d)}"
VDPAUSUPPORT ?= "${@bb.utils.contains('DISTRO_FEATURES', 'x11', '1', '0', d)}"
