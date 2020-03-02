inherit kodi-common

PACKAGECONFIG[vidstab] = "--enable-libvidstab,--disable-libvidstab,vid.stab"
PACKAGECONFIG[x265] = "--enable-libx265,--disable-libx265,x265"

PACKAGECONFIG_append = " ${KODIADDITIONALCODECS} ${KODIACCELERATIONLIBRARIES}"
