SUMMARY = "Silicondust HDHomeRun tuner control library"
HOMEPAGE = "https://github.com/Silicondust/libhdhomerun"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fc178bcd425090939a8b634d1d6a9594"

SRC_URI = "git://github.com/Silicondust/libhdhomerun.git;branch=master;protocol=https;tag=${PV}"
SRCREV = "dc48af56d3c338288765b263e57129f711dedb9b"

# Plain Makefile, no configure step and no install target -- the
# CLI tool (hdhomerun_config) is skipped, only the library is needed.
EXTRA_OEMAKE = "CC='${CC}' CFLAGS='${CFLAGS} -fPIC' LDFLAGS='${LDFLAGS}'"

do_compile() {
    oe_runmake libhdhomerun.so
}

do_install() {
    install -d ${D}${includedir}/hdhomerun
    install -m 0644 ${S}/hdhomerun*.h ${D}${includedir}/hdhomerun/
    install -d ${D}${libdir}
    install -m 0755 ${S}/libhdhomerun.so ${D}${libdir}/
}

# Upstream ships an unversioned .so with no SONAME (pvr.hdhomerun's own
# CMake Find module links against it as-is), and no real dev/runtime
# split exists here -- one library file, no separate headers-only vs
# runtime concept. bitbake.conf's default FILES:${PN}-dev pulls in
# ${libdir}/lib*.so via ${FILES_SOLIBSDEV} (a glob token, not the literal
# filename -- a plain :remove of the expanded path does not match it).
# Overriding FILES:${PN}-dev outright to just the headers is the real fix.
FILES:${PN} += "${libdir}/libhdhomerun.so"
FILES:${PN}-dev = "${includedir}"
