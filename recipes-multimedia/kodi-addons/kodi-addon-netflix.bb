SUMMARY = "Netflix Plugin for Kodi."
HOMEPAGE = "https:/github.com/CastagnaIT/plugin.video.netflix"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=2532aa690bfeebabd9ff611d964f4cd8"

RDEPENDS:${PN} = "kodi-addon-signals kodi-addon-inputstreamhelper mysql-connector-python python3-unixadmin"

PV = "1.18.8"

SRCREV = "dfa358cd0a4912a4930342a13a654a79dde65dce"
SRC_URI = "git://github.com/CastagnaIT/plugin.video.netflix.git;branch=master;protocol=https"

S = "${WORKDIR}/git"

KODIADDONNAME = "plugin.video.netflix"
KODIADDONDIR = "${datadir}/kodi/addons"

do_compile() {
	:
}

do_install() {
	sed -i "s|0.0.6+matrix.1|0.0.6|" ${S}/addon.xml
	sed -i "s|0.5.0+matrix.1|0.5.0|" ${S}/addon.xml
	sed -i "s|8.0.18+matrix.1|8.0.18|" ${S}/addon.xml
	install -d ${D}${KODIADDONDIR}/${KODIADDONNAME}
	install -m644 ${S}/addon.xml ${D}${KODIADDONDIR}/${KODIADDONNAME}
	install -m755 ${S}/service.py ${D}${KODIADDONDIR}/${KODIADDONNAME}
	install -m755 ${S}/addon.py ${D}${KODIADDONDIR}/${KODIADDONNAME}
	install -m644 ${S}/LICENSE.md ${D}${KODIADDONDIR}/${KODIADDONNAME}
	cp -rf ${S}/resources ${D}${KODIADDONDIR}/${KODIADDONNAME}
	cp -rf ${S}/packages ${D}${KODIADDONDIR}/${KODIADDONNAME}
}

FILES:${PN} = "${KODIADDONDIR}"

