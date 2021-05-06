LDFLAGS += "${TOOLCHAIN_OPTIONS}"
LDFLAGS_append_mipsarch = " -latomic -lpthread"

EXTRA_OECMAKE_append_mipsarch = " -DWITH_ARCH=${TARGET_ARCH}"
