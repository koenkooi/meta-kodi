SUMMARY = "Fuzzy comparison of strings and byte arrays"
DESCRIPTION = "The fstrcmp project provides a library that is used to make \
fuzzy comparisons of strings and byte arrays, including multi-byte character \
strings. Kodi uses it for approximate string matching in its GUI."
HOMEPAGE = "https://fstrcmp.sourceforge.net/"

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=84269fe9822f573d2cc8131b63febc57"

DEPENDS = "groff-native"

SRC_URI = "${SOURCEFORGE_MIRROR}/fstrcmp/${PV}/fstrcmp-${PV}.D001.tar.gz \
           file://nopdf.patch \
           "
SRC_URI[sha256sum] = "e4018e850f80700acee8da296e56e15b1eef711ab15157e542e7d7e1237c3476"

S = "${UNPACKDIR}/${BPN}-${PV}.D001"

inherit autotools-brokensep

BBCLASSEXTEND = "native nativesdk"
