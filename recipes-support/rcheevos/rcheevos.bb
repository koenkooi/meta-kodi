SUMMARY = "rcheevos is a set of C code that tries to make it easier for emulators to process RetroAchievements data."
HOMEPAGE = "https://github.com/RetroAchievements/rcheevos"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=466b936a0a89a7677882b9a92c189c2f"


SRCREV = "203fc282d542a058241f3c5a28a2d0ca15e628cb"
PV = "9.2.0"
S = "${WORKDIR}/git"

SRC_URI = " \
	git://github.com/RetroAchievements/rcheevos.git;nobranch=1;protocol=https \
	file://CMakeLists.txt \	
"

do_configure:prepend() {
	cp ${WORKDIR}/CMakeLists.txt ${S}
}

inherit cmake

EXTRA_OECMAKE += "-DCMAKE_POSITION_INDEPENDENT_CODE=ON"
