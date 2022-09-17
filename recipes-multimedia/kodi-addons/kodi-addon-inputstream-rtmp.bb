SUMMARY = "kodi inputstream addon for rtmp"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit kodi-addon

PV = "20.3.0"

SRCREV = "61f7ff342b5c60ff5c9d75f6de843fe4b8cd7aa7"
SRC_URI = "git://github.com/xbmc/inputstream.rtmp.git;protocol=https;nobranch=1"
      
S = "${WORKDIR}/git"

KODIADDONNAME = "inputstream.rtmp"

