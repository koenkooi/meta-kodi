SUMMARY = "OSCam: Open Source Conditional Access Module"
HOMEPAGE = "http://www.streamboard.tv/oscam/"

LICENSE = "GPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

DEPENDS = "libusb1 openssl pcsc-lite"

# Real upstream (svn://www.streamboard.tv/svn/oscam) and the best-known
# community continuation (oscam-emu/oscam-patched) are both gone. This is
# the oscam-svn branch of github.com/Toysoft/oscam-patched_20230617,
# a git-svn mirror of plain streamboard.tv trunk (no WITH_EMU/SoftCam code,
# confirmed emu-free at this pin) running through SVN r11719, 2023-06-06 --
# a genuine continuation of the personal r11273 checkout this recipe used
# to vendor as a tarball, verified byte-identical at that revision before
# switching to this pin. Patches on top are real fixes for bugs still
# present at r11719 plus the meson build conversion (no equivalent exists
# upstream); see each patch and ~/oe/work/oscam (a durable local checkout,
# separate from this tarball-free recipe) for the fuller history of what
# was checked against upstream and dropped because it was already fixed
# there versus what still needed a patch.
SRC_URI = " \
    git://github.com/Toysoft/oscam-patched_20230617.git;branch=oscam-svn;protocol=https \
    file://0001-reader-cryptoworks-fix-a-latent-NULL-deref-in-upstre.patch \
    file://0002-module-webif-check-popen-return-before-using-it.patch \
    file://0003-oscam-bound-the-trailing-slash-append-on-cs_confdir.patch \
    file://0004-csctapi-ifd_db2com-include-sys-sysmacros.h-for-major.patch \
    file://0005-webif-pages_gen-allow-out-of-tree-generation.patch \
    file://0006-webif-pages_gen-read-is_defined.txt-from-output_dir-.patch \
    file://0007-build-add-a-meson-build-system.patch \
    file://0008-config.sh-keep-the-objdir-out-of-the-generated-confi.patch \
    file://0009-build-expose-READER_NAGRA_MERLIN-off-by-default.patch \
    file://0010-build-generate-config-meson.h-not-config.h-avoid-an-.patch \
"
SRCREV = "927208a3d85c57525a3e021068ab3699b118c23f"

PV = "1.20+svn11719"

inherit meson

# The reader/protocol/addon matrix is real meson options now (see
# meson_options.txt upstream), not config.sh -- their defaults match the old
# config.h defconfig, so only the revision string is overridden here.
EXTRA_OEMESON = "-Dsvn-revision=11719"
