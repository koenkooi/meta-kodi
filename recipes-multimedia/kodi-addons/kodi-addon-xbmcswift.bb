SUMMARY = "This is an automatically generated repository for the KODI distribution of xbmcswift2."
HOMEPAGE = "https://github.com/XBMC-Addons/script.module.xbmcswift2"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=d32239bcb673463ab874e80d47fae504"

PV = "19.0.7"

SRCREV = "78530fd959284d8c3cc6a2178551c9c18423b42c"
SRC_URI = "git://github.com/XBMC-Addons/script.module.xbmcswift2.git;branch=matrix;protocol=https"

S = "${WORKDIR}/git"

KODIADDONNAME = "script.module.xbmcswift2"
KODIADDONDIR = "${datadir}/kodi/addons"

do_compile() {
	:
}

do_install() {
	install -d ${D}${KODIADDONDIR}/${KODIADDONNAME}
	install -m644 ${S}/icon.png ${D}${KODIADDONDIR}/${KODIADDONNAME}
	install -m644 ${S}/addon.xml ${D}${KODIADDONDIR}/${KODIADDONNAME}
	install -m644 ${S}/LICENSE.txt ${D}${KODIADDONDIR}/${KODIADDONNAME}
	cp -rf ${S}/lib ${D}${KODIADDONDIR}/${KODIADDONNAME}
}

FILES:${PN} = "${KODIADDONDIR}"

