SUMMARY = "Reusable coding blocks useful for libretro core and frontend development, written primarily in C. Permissively licensed."
HOMEPAGE = "https://github.com/libretro/libretro-common.git"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://audio/audio_mix.c;md5=adabae0cee703870552dde81379cbe1a"


SRCREV = "99cf1facd5ef0946a5f903f8f892a10011c792af"

SRC_URI = " \
	git://github.com/libretro/libretro-common.git;branch=master;protocol=https \
	file://CMakeLists.txt \	
"

S = "${WORKDIR}/git"

do_configure:prepend() {
	cp ${WORKDIR}/CMakeLists.txt ${S}
}

inherit cmake
