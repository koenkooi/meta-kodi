SUMMARY = "Bento4 MP4, fragmented MP4 (DASH) and CENC library and tools"
HOMEPAGE = "https://github.com/xbmc/Bento4"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://Documents/LICENSE.txt;md5=2bdfce88f437a0613f41effed74b7061"

# xbmc's own fork, not plain upstream axiomatic-systems/Bento4 -- Kodi's
# inputstream.adaptive is written against xbmc-specific API additions
# (protected AP4_SgpdAtom::m_GroupingType, AP4_AvcSequenceParameterSet::
# GetVUIInfo, AP4_VpccAtom::GetData, a widened AP4_LinearReader::ProcessMoof
# signature) that plain upstream Bento4 does not have -- confirmed by a
# real build failure against plain upstream first, then confirmed present
# here before switching.
SRC_URI = "git://github.com/xbmc/Bento4.git;nobranch=1;protocol=https"
SRCREV = "a4a4172467e592c17c7d823f81196e77e4486f91"

inherit cmake

# inputstream.adaptive only needs the Ap4 library + headers, not the
# mp4info/mp4dump/mp4encrypt CLI tools. It links this static lib into
# its own shared object, so it needs PIC code -- real link failure
# without CMAKE_POSITION_INDEPENDENT_CODE (relocation errors building
# the addon .so against a non-PIC libap4.a).
EXTRA_OECMAKE = "-DBUILD_APPS=OFF -DCMAKE_POSITION_INDEPENDENT_CODE=ON"
