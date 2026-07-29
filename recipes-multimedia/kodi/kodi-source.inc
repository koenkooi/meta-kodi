# Shared source definition for the Kodi 22 "Piers" recipes (the media
# center itself and the native build tools split out of its tree).
#
# 22.0b1-Piers is the real upstream beta 1 pre-release tag
# (https://github.com/xbmc/xbmc/releases/tag/22.0b1-Piers, published
# 2026-06-21); SRCREV tracks master past that tag -- no newer upstream
# tag exists yet, and no "Piers" release branch exists either.
#
# PV uses the "<last-stable>+<target>-<tag>" convention (21 is Kodi 21
# "Omega", the last real release) instead of a plain "22.0-beta3": most
# packagemanagers, opkg included, treat a bare "-betaN" suffix as
# sorting *above* the final "22.0" once that ships, which would look
# like a downgrade. "21+22.0-beta3" always sorts between "21" and
# "22.0".

KODI_CODENAME = "Piers"

LICENSE = "GPL-2.0-or-later"
# kodi-jsonschemabuilder-native builds from a subdirectory of the Kodi
# tree that carries its own (GPL-2.0-only) LICENSE.GPL and overrides
# this to match -- that's intentional, not an accidental duplicate.
# nooelint: oelint.var.override
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=7b423f1c9388eae123332e372451a4f7"

PV = "21+22.0-beta3"

SRC_URI = "git://github.com/xbmc/xbmc.git;protocol=https;branch=master;destsuffix=kodi-${PV}"
SRCREV = "77395cf42e141000b0475b8d5a54925ad67bfeba"

# kodi-jsonschemabuilder-native overrides this to the tool's own
# subdirectory inside the Kodi tree -- intentional, see the recipe.
# nooelint: oelint.var.override
S = "${UNPACKDIR}/kodi-${PV}"

UPSTREAM_CHECK_GITTAGREGEX = "^(?P<pver>22\.\d+[a-z0-9]*)-${KODI_CODENAME}$"
