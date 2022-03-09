SUMMARY = "kodi inputstream addon for rtmp"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit kodi-addon

PV = "20.1.0"

SRCREV = "10640f8a721e302e7cbb5788ebd56a61529b257f"
SRC_URI = "git://github.com/xbmc/inputstream.rtmp.git;protocol=https;nobranch=1"
      
S = "${WORKDIR}/git"

KODIADDONNAME = "inputstream.rtmp"

