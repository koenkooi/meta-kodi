LDFLAGS += "${TOOLCHAIN_OPTIONS}"
LDFLAGS_append_mipsarch = " -latomic"

EXTRA_OECMAKE_append_mipsarch = " -DWITH_ARCH=${TARGET_ARCH}"
