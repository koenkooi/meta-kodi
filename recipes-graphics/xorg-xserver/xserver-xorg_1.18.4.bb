require xserver-xorg.inc

SRCREV = "8b312db7d1d98be67f0283d982428545cf948a66"

SRC_URI = " git://anongit.freedesktop.org/git/xorg/xserver.git;protocol=https;branch=server-1.18-branch \
            file://macro_tweak.patch \
            file://musl-arm-inb-outb.patch \
           "

S = "${WORKDIR}/git"

# These extensions are now integrated into the server, so declare the migration
# path for in-place upgrades.

RREPLACES_${PN} =  "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "
RPROVIDES_${PN} =  "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "
RCONFLICTS_${PN} = "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "
