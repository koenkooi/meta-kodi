SUMMARY = "Kodi signal addon."
HOMEPAGE = "https://github.com/ruuk/script.module.addon.signals"

LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=243b725d71bb5df4a1e5920b344b86ad"

PV = "0.0.6"

SRCREV = "ff50af0673de6b43cb47f4e45af1bc9d815d1e52"
SRC_URI = "git://github.com/ruuk/script.module.addon.signals.git;branch=master;protocol=https"

S = "${WORKDIR}/git"

KODIADDONNAME = "script.module.addon.signals"
KODIADDONDIR = "${datadir}/kodi/addons"

do_compile() {
	:
}

do_install() {
	install -d ${D}${KODIADDONDIR}/${KODIADDONNAME}
	sed -i "s|2.14.0|3.0.0|" ${S}/addon.xml
	install -m644 ${S}/icon.png ${D}${KODIADDONDIR}/${KODIADDONNAME}
	install -m644 ${S}/addon.xml ${D}${KODIADDONDIR}/${KODIADDONNAME}
	install -m644 ${S}/LICENSE.txt ${D}${KODIADDONDIR}/${KODIADDONNAME}
	cp -rf ${S}/lib ${D}${KODIADDONDIR}/${KODIADDONNAME}
}

FILES:${PN} = "${KODIADDONDIR}"

