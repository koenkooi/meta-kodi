SUMMARY = "Kodi JsonSchemaBuilder build tool"
DESCRIPTION = "Generates the JSON-RPC schema sources at build time. Built \
from the Kodi source tree and passed to the main kodi recipe via \
-DWITH_JSONSCHEMABUILDER."
HOMEPAGE = "https://kodi.tv/"

require kodi-source.inc

LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=a8119afbffe1b8b7cd2e751480b80ad8"

# The tool's standalone CMakeLists.txt uses install(TARGETS ... RENAME),
# which CMake rejects at configure time, so build the proven autotools
# project in the same directory instead.
S = "${UNPACKDIR}/kodi-${PV}/tools/depends/native/JsonSchemaBuilder/src"

inherit autotools
inherit_defer native
