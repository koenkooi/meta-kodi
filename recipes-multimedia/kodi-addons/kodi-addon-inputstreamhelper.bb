SUMMARY = "Kodi inputstream addon for several manifest types"
HOMEPAGE = "https://github.com/emilsvennesson/script.module.inputstreamhelper"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=e7d24110ae7397fb8d7bbe5265aac078"

inherit setuptools3

PV = "0.5.8"

SRCREV = "b21b228c22309ea62ec90627c983fa42ce7c7d4d"
SRC_URI = " \
	git://github.com/emilsvennesson/script.module.inputstreamhelper.git;nobranch=1;protocol=https \
	file://0001-kodi-addon-inputstreamhelper-avoid-distutils-usage.patch \
"

S = "${WORKDIR}/git"

KODIADDONNAME = "script.module.inputstreamhelper"


