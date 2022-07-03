SUMMARY = "kodi inputstream addon for ffmpeg"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit kodi-addon

PV = "20.2.3"

SRCREV = "c10a4787398132359ee721f88df7f8d98b76b0ff"
SRC_URI = "git://github.com/xbmc/inputstream.ffmpegdirect.git;protocol=https;nobranch=1"

S = "${WORKDIR}/git"

KODIADDONNAME = "inputstream.ffmpegdirect"

