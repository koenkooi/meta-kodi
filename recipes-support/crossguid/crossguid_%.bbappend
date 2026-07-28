# The exported cmake config records libuuid as an absolute path, which
# consumers cannot reroot into their own sysroot; link by name instead.
do_install:append() {
    sed -i -e 's|"[^"]*/libuuid\.so"|"uuid"|' ${D}${datadir}/crossguid/cmake/crossguid-config.cmake
}
