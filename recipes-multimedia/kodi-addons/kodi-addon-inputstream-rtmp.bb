SUMMARY = "kodi inputstream addon for rtmp"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit kodi-addon

PV = "20.2.0"

SRCREV = "d3be647f7db255b70a3b72ad5ad20c2c9176945d"
SRC_URI = "git://github.com/xbmc/inputstream.rtmp.git;protocol=https;nobranch=1"
      
S = "${WORKDIR}/git"

KODIADDONNAME = "inputstream.rtmp"

