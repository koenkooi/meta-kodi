SUMMARY = "kodi inputstream addon for rtmp"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit kodi-addon

PV = "3.4.0"

SRCREV = "${PV}-${KODICODENAME}"
SRC_URI = "git://github.com/xbmc/inputstream.rtmp.git;protocol=https;nobranch=1"
      
S = "${WORKDIR}/git"

KODIADDONNAME = "inputstream.rtmp"

