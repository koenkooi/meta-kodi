SUMMARY = "Bento4 is a C++ class library and tools designed to read and write ISO-MP4 files."
HOMEPAGE = "https://github.com/axiomatic-systems/Bento4"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://Documents/LICENSE.txt;md5=2bdfce88f437a0613f41effed74b7061"


inherit cmake pkgconfig

SRC_URI = " \
	git://github.com/axiomatic-systems/Bento4.git;protocol=https;branch=master \
	file://0001-Add-additional-methods-funtions-and-passing-poolid.patch \
	file://0002-Backport-Smmothstream-changes.patch \
	file://0003-more-SPS-parameters.patch \
	file://0004-AVC-extract-VUI-values-from-SPS.patch \
	file://0005-Implement-SPS-Frame-parser.patch \
	file://0006-Fix-segfault-when-AP4_Sample-s-seek.patch \
	file://0007-Hack-HBO.patch \
	file://0008-Android-32-ftello-fix.patch \
	file://0009-Dazn-sample-duration-workaround.patch \
	file://0010-Add-argument-to-reuse-single-sample-decrypter.patch \
	file://0011-Static-ReadGolomb-SignedGolomb.patch \
	file://0012-Add-GetChannels-method.patch \
	file://0013-Implemented-GetSampleIndexForTimeStamp-GetNearestSyn.patch \
	file://0014-Ap4SampleDescription-added-missing-dynamic-casts.patch \
	file://0015-Avoid-set-next-fragment-position.patch \
	file://0016-Add-support-for-cmake-install.patch \
"

PV = "1.6.0-639"
SRCREV = "v${PV}"
S = "${WORKDIR}/git"

EXTRA_OECMAKE = "-DBUILD_APPS=OFF -DCMAKE_POSITION_INDEPENDENT_CODE=ON -DCMAKE_BUILD_TYPE=Release"
