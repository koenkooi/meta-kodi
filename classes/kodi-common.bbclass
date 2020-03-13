#Acceleration

VAAPISUPPORT ?= "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', '1', '0', d)}"
VDPAUSUPPORT ?= "${@bb.utils.contains('DISTRO_FEATURES', 'x11', '1', '0', d)}"

# Variables

KODICODENAME ?= "Leia"

# Default to GBM everywhere, sucks to be nvidia

KODIGRAPHICALBACKEND ?= "gbm"

# Autostart

KODISYSTEMDAUTOSTART ?= "enable"
