SUMMARY = "Kodi-Addon-Radio."
HOMEPAGE = "https://github.com/xbmc/repo-plugins"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

RDEPENDS:${PN} = "python3-six kodi-addon-xbmcswift"

SRC_URI = "git://github.com/xbmc/repo-plugins.git;branch=matrix;protocol=https"
SRCREV = "3d228f280813144aff59f7472ea3fd7a88b786d1"
S = "${WORKDIR}/git/${KODIADDONNAME}"
PV = "3.0.9"

KODIADDONNAME = "plugin.audio.radio_de"
KODIADDONDIR = "${datadir}/kodi/addons"

do_compile() {
	:
}

do_install() {
	install -d ${D}${KODIADDONDIR}/${KODIADDONNAME}
	install -m644 ${S}/addon.xml ${D}${KODIADDONDIR}/${KODIADDONNAME}
	install -m644 ${S}/LICENSE.txt ${D}${KODIADDONDIR}/${KODIADDONNAME}
	install -m755 ${S}/addon.py ${D}${KODIADDONDIR}/${KODIADDONNAME}
	cp -rf ${S}/resources ${D}${KODIADDONDIR}/${KODIADDONNAME}
}

FILES:${PN} = "${KODIADDONDIR}"

