do_install_append() {
    install -d ${D}${includedir}
    install -m 0644 ${B}/include/ffi.h ${D}${includedir}
}
