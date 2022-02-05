SUMMARY = "Kodi-Addon-ARDundZDF."
HOMEPAGE = "https://github.com/rols1/Kodi-Addon-ARDundZDF"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=883ff1a665ed91229ca96e1ede954ae2"

RDEPENDS:${PN} = "python3-six kodi-addon-kodi-six"

SRC_URI = "git://github.com/rols1/Kodi-Addon-ARDundZDF.git;branch=master;protocol=https"
SRCREV = "31b6814bc596b8cd041687a3f04ad96a8fbf93a5"
S = "${WORKDIR}/git"
PV = "4.2.2"

KODIADDONNAME = "plugin.video.ardundzdf"
KODIADDONDIR = "${datadir}/kodi/addons"

do_compile() {
	:
}

do_install() {
	install -d ${D}${KODIADDONDIR}/${KODIADDONNAME}
	install -m644 ${S}/icon.png ${D}${KODIADDONDIR}/${KODIADDONNAME}
	install -m644 ${S}/fanart.jpg ${D}${KODIADDONDIR}/${KODIADDONNAME}
	install -m644 ${S}/addon.xml ${D}${KODIADDONDIR}/${KODIADDONNAME}
	install -m644 ${S}/LICENSE.txt ${D}${KODIADDONDIR}/${KODIADDONNAME}
	install -m755 ${S}/ardundzdf.py ${D}${KODIADDONDIR}/${KODIADDONNAME}
	cp -rf ${S}/resources ${D}${KODIADDONDIR}/${KODIADDONNAME}
}

FILES:${PN} = "${KODIADDONDIR}"

