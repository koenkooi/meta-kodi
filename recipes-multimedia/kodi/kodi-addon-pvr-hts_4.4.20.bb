SUMMARY = "Kodi's Tvheadend HTSP client addon"
HOMEPAGE = "https://github.com/kodi-pvr/pvr.hts"
BUGTRACKER = "https://github.com/kodi-pvr/pvr.hts/issues"

# Project have missing license, https://github.com/kodi-pvr/pvr.hts/issues/441
# Assuming GPLv2

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

inherit kodi-addon

SRCREV = "${PV}-${KODICODENAME}"
SRC_URI = "git://github.com/kodi-pvr/pvr.hts.git;nobranch=1;protocol=https"
S = "${WORKDIR}/git"

KODIADDONNAME = "pvr.hts"
