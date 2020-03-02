# Should we create notice to inform users that do not want X11 support and get vdpau support?

KODIACCELERATIONLIBRARIESX86DEFAULTS ?= " \
  ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'vdpau', '', d)} \
  ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'vaapi', '', d)} \
"

KODIACCELERATIONLIBRARIES ?= ""
KODIACCELERATIONLIBRARIES_x86 = "${KODIACCELERATIONLIBRARIESX86DEFAULTS}"
KODIACCELERATIONLIBRARIES_x86-64 = "${KODIACCELERATIONLIBRARIESX86DEFAULTS}"

# Variables

KODICODENAME ?= "Leia"

# Default to GBM everywhere, sucks to be nvidia

KODIGRAPHICALBACKEND ?= "gbm"

# Codecs

KODIADDITIONALCODECS ?= "vidstab x265" 

# Autostart

KODISYSTEMDAUTOSTART ?= "enable"
