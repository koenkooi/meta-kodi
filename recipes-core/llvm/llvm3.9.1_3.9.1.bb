DESCRIPTION = "The Low Level Virtual Machine"
HOMEPAGE = "http://llvm.org"

# 3-clause BSD-like
# University of Illinois/NCSA Open Source License
LICENSE = "NCSA"
LIC_FILES_CHKSUM = "file://LICENSE.TXT;md5=b99eb43c934ceebecab85c6b9b1a08be"

DEPENDS = "libffi libxml2-native llvm-common zlib ninja-native"
RDEPENDS_${PN} += "ncurses-terminfo"

inherit perlnative pythonnative cmake

PROVIDES += "llvm"

LLVM_RELEASE = "${PV}"
LLVM_DIR = "llvm${LLVM_RELEASE}"

SRCREV = "a093ef43dd592b729da46db4ff3057fef9a46023"
PV = "3.9.1"
SRC_URI = "git://llvm.org/git/llvm.git;branch=release_39;protocol=http \
           file://0001-CrossCompile.cmake-adjust-build-for-OE.patch \
           file://0002-CrossCompile.cmake-use-target-BuildVariables-include.patch \
           file://0003-Cleanup-LLVM_OPTIMIZED_TABLEGEN.patch \
           file://0004-Dont-build-llvm-config-and-tblgen-concurrently.patch \
           file://0005-CrossCompile.cmake-strip-sysroot-info-from-build-var.patch"
S = "${WORKDIR}/git"

LLVM_INSTALL_DIR = "${WORKDIR}/llvm-install"

CXXFLAGS_remove = "${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS}"
CFLAGS_remove = "${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS}"
EXTRA_OECMAKE += "-DLLVM_ENABLE_ASSERTIONS=OFF \
                  -DLLVM_ENABLE_EXPENSIVE_CHECKS=OFF \
                  -DLLVM_BINDINGS_LIST="" \
                  -DLLVM_LINK_LLVM_DYLIB=ON \
                  -DLLVM_ENABLE_FFI=ON \
                  -DLLVM_OPTIMIZED_TABLEGEN=ON \
                  -DLLVM_TARGETS_TO_BUILD="AMDGPU;X86""

do_configure() {
    # Fix paths in llvm-config
    sed -i "s|sys::path::parent_path(CurrentPath))\.str()|sys::path::parent_path(sys::path::parent_path(CurrentPath))).str()|g" ${S}/tools/llvm-config/llvm-config.cpp
    sed -ri "s#/(bin|include|lib)(/?\")#/\1/${LLVM_DIR}\2#g" ${S}/tools/llvm-config/llvm-config.cpp
    sed -ri "s#lib/${LLVM_DIR}#${baselib}/${LLVM_DIR}#g" ${S}/tools/llvm-config/llvm-config.cpp
    cd ${B}
    cmake \
      -G Ninja \
      ${S} \
      -DCMAKE_INSTALL_PREFIX:PATH=/usr \
      -DCMAKE_INSTALL_BINDIR:PATH=bin \
      -DCMAKE_INSTALL_SBINDIR:PATH=sbin \
      -DCMAKE_INSTALL_LIBEXECDIR:PATH=libexec \
      -DCMAKE_INSTALL_SYSCONFDIR:PATH=/etc \
      -DCMAKE_INSTALL_SHAREDSTATEDIR:PATH=../com \
      -DCMAKE_INSTALL_LOCALSTATEDIR:PATH=/var \
      -DCMAKE_INSTALL_LIBDIR:PATH=lib64 \
      -DCMAKE_INSTALL_INCLUDEDIR:PATH=include \
      -DCMAKE_INSTALL_DATAROOTDIR:PATH=share \
      -DCMAKE_INSTALL_SO_NO_EXE=0 \
      -DCMAKE_TOOLCHAIN_FILE=${WORKDIR}/toolchain.cmake \
      -DCMAKE_VERBOSE_MAKEFILE=1 \
      -DCMAKE_NO_SYSTEM_FROM_IMPORTED=1 \
      ${EXTRA_OECMAKE}
}

do_compile() {
    cd ${B}
    NINJA_STATUS="[%p] " ninja -v
}

do_install() {
    DESTDIR=${LLVM_INSTALL_DIR} ninja -v install

    install ${B}/NATIVE/bin/llvm-config ${LLVM_INSTALL_DIR}/llvm-config-host

    install -d ${D}${bindir}/${LLVM_DIR}
    cp -r ${LLVM_INSTALL_DIR}${bindir}/* ${D}${bindir}/${LLVM_DIR}/

    install -d ${D}${includedir}/${LLVM_DIR}
    cp -r ${LLVM_INSTALL_DIR}${includedir}/* ${D}${includedir}/${LLVM_DIR}/

    install -d ${D}${libdir}/${LLVM_DIR}

    # The LLVM sources have "/lib" embedded and so we cannot completely rely on the ${libdir} variable
    if [ -d ${LLVM_INSTALL_DIR}${libdir}/ ]; then
        cp -r ${LLVM_INSTALL_DIR}${libdir}/* ${D}${libdir}/${LLVM_DIR}/
    elif [ -d ${LLVM_INSTALL_DIR}${prefix}/lib ]; then
        cp -r ${LLVM_INSTALL_DIR}${prefix}/lib/* ${D}${libdir}/${LLVM_DIR}/
    elif [ -d ${LLVM_INSTALL_DIR}${prefix}/lib64 ]; then
        cp -r ${LLVM_INSTALL_DIR}${prefix}/lib64/* ${D}${libdir}/${LLVM_DIR}/
    fi

    # Remove unnecessary cmake files
    rm -rf ${D}${libdir}/${LLVM_DIR}/cmake

    ln -s ${LLVM_DIR}/libLLVM-${PV}${SOLIBSDEV} ${D}${libdir}/libLLVM-${PV}${SOLIBSDEV}

    # We'll have to delete the libLLVM.so due to multiple reasons...
    rm -rf ${D}${libdir}/${LLVM_DIR}/libLLVM.so
}

SYSROOT_PREPROCESS_FUNCS += "llvm_sysroot_preprocess"

llvm_sysroot_preprocess() {
    install -d ${SYSROOT_DESTDIR}${bindir_crossscripts}
    cp ${LLVM_INSTALL_DIR}/llvm-config-host ${SYSROOT_DESTDIR}${bindir_crossscripts}/llvm-config${PV}
}

PACKAGES += "${PN}-bugpointpasses ${PN}-llvmhello"
ALLOW_EMPTY_${PN} = "1"
ALLOW_EMPTY_${PN}-staticdev = "1"
FILES_${PN} = ""
FILES_${PN}-staticdev = ""
FILES_${PN}-dbg = " \
    ${bindir}/${LLVM_DIR}/.debug \
    ${libdir}/${LLVM_DIR}/.debug/BugpointPasses.so \
    ${libdir}/${LLVM_DIR}/.debug/LLVMHello.so \
    /usr/src/debug \
"

FILES_${PN}-dev = " \
    ${bindir}/${LLVM_DIR} \
    ${includedir}/${LLVM_DIR} \
"
RRECOMMENDS_${PN}-dev += "${PN}-bugpointpasses ${PN}-llvmhello"

FILES_${PN}-bugpointpasses = "\
    ${libdir}/${LLVM_DIR}/BugpointPasses.so \
"
FILES_${PN}-llvmhello = "\
    ${libdir}/${LLVM_DIR}/LLVMHello.so \
"

PACKAGES_DYNAMIC = "^libllvm${LLVM_RELEASE}-.*$"
NOAUTOPACKAGEDEBUG = "1"

INSANE_SKIP_${MLPREFIX}libllvm${LLVM_RELEASE}-llvm-${LLVM_RELEASE} += "dev-so"
INSANE_SKIP_${MLPREFIX}libllvm${LLVM_RELEASE}-llvm += "dev-so"

python llvm_populate_packages() {
    libdir = bb.data.expand('${libdir}', d)
    libllvm_libdir = bb.data.expand('${libdir}/${LLVM_DIR}', d)
    split_dbg_packages = do_split_packages(d, libllvm_libdir+'/.debug', '^lib(.*)\.so$', 'libllvm${LLVM_RELEASE}-%s-dbg', 'Split debug package for %s', allow_dirs=True)
    split_packages = do_split_packages(d, libdir, '^lib(.*)\.so$', 'libllvm${LLVM_RELEASE}-%s', 'Split package for %s', allow_dirs=True, allow_links=True, recursive=True)
    split_staticdev_packages = do_split_packages(d, libllvm_libdir, '^lib(.*)\.a$', 'libllvm${LLVM_RELEASE}-%s-staticdev', 'Split staticdev package for %s', allow_dirs=True)
    if split_packages:
        pn = d.getVar('PN', True)
        d.appendVar('RDEPENDS_' + pn, ' '+' '.join(split_packages))
        d.appendVar('RDEPENDS_' + pn + '-dbg', ' '+' '.join(split_dbg_packages))
        d.appendVar('RDEPENDS_' + pn + '-staticdev', ' '+' '.join(split_staticdev_packages))
}

PACKAGESPLITFUNCS_prepend = "llvm_populate_packages "
