SUMMARY = "MKVToolNix -- Cross-platform tools for Matroska"
HOMEPAGE = "http://www.bunkus.org/videotools/mkvtoolnix/source.html"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "curl boost expat zlib libogg libvorbis bzip2 lzo file ruby-native docbook-xsl-stylesheets docbook-xml-dtd4 libxml2-native libxslt-native xmlto-native docbook-xml-dtd4-native docbook-xsl-stylesheets-native"

PV = "42.0.0+git${SRCPV}"
SRCREV_mkvtoolnix = "64b1c3d9b380731ba94eae6a613dded87fa05d79"
SRCREV_FORMAT = "mkvtoolnix"
SRC_URI = " \
           gitsm://gitlab.com/mbunkus/mkvtoolnix.git;protocol=https;name=mkvtoolnix;branch=main \
          "

S = "${WORKDIR}/git"

inherit autotools-brokensep gettext

# make sure rb files are used from sysroot, not from host
# ruby-1.9.3-always-use-i386.patch is doing target_cpu=`echo $target_cpu | sed s/i.86/i386/`
# we need to replace it too (a bit longer version without importing re)
RUBY_SYS = "${@ '${BUILD_SYS}'.replace('i486', 'i386').replace('i586', 'i386').replace('i686', 'i386') }"
export RUBYLIB="${STAGING_DATADIR_NATIVE}/rubygems:${STAGING_LIBDIR_NATIVE}/ruby:${STAGING_LIBDIR_NATIVE}/ruby/${RUBY_SYS}"

PACKAGECONFIG ??= "flac"
PACKAGECONFIG[flac] = "--with-flac,--without-flac,flac"
PACKAGECONFIG[qt5] = "--enable-qt --with-moc=${STAGING_BINDIR_NATIVE}/qt5/moc --with-uic=${STAGING_BINDIR_NATIVE}/qt5/uic --with-rcc=${STAGING_BINDIR_NATIVE}/qt5/rcc,--disable-qt,qtbase"

EXTRA_OECONF = " --with-boost-libdir=${STAGING_LIBDIR} \
                 --with-docbook-xsl-root=${STAGING_DATADIR_NATIVE}/xml/docbook/xsl-stylesheets/ \
		 --bindir=${bindir} \
                 --exec-prefix=${prefix} \
"

FILES_${PN} += "${datadir}"

# remove some hardcoded searchpaths
do_configure_prepend() {
    sed -i -e s:/usr/local/lib:${STAGING_LIBDIR}:g -e s:/usr/local/include:${STAGING_INCDIR}:g ${S}/ac/qt5.m4
}

# Yeah, no makefile
do_compile() {
    LC_ALL="en_US.UTF-8" rake ${PARALLEL_MAKE}
}

do_install() {
    LC_ALL="en_US.UTF-8" rake install DESTDIR=${D}
    mkdir -p ${D}${bindir}
    mv ${D}${STAGING_BINDIR_NATIVE}/* ${D}${bindir}
    rm -rf ${D}/$(echo ${STAGING_BINDIR_NATIVE}| awk -F/ '{print $2}')
}

# | In file included from src/common/utf8_codecvt_facet.cpp:22:0:
# | src/common/../../lib/boost/utf8_codecvt_facet/utf8_codecvt_facet.cpp:174:5: error: 'int mtx::utf8_codecvt_facet::do_length' is not a static data member of 'struct mtx::utf8_codecvt_facet'
# |      BOOST_CODECVT_DO_LENGTH_CONST std::mbstate_t &,
# |      ^

