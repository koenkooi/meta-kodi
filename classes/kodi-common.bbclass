# Should we create notice to inform users that do not want X11 support and get vdpau support?

KODI_ACCELERATION_LIBRARIES_X86_DEFAULTS ?= " \
  ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'vdpau', '', d)} \
  ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'vaapi', '', d)} \
"

KODI_ACCELERATION_LIBRARIES ?= ""
KODI_ACCELERATION_LIBRARIES_x86 = "${KODI_ACCELERATION_LIBRARIES_X86_DEFAULTS}"
KODI_ACCELERATION_LIBRARIES_x86-64 = "${KODI_ACCELERATION_LIBRARIES_X86_DEFAULTS}"

# Default to GBM everywhere, sucks to be nvidia

KODI_GRAPHICAL_BACKEND ?= "gbm"

# Codecs

KODI_ADDITIONAL_CODES ?= "vidstab x265" 

# Autostart

KODI_SYSTEMD_AUTOSTART ?= "enable"
