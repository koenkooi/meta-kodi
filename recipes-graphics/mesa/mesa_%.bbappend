PACKAGECONFIG:append:class-target = " \
    ${@bb.utils.contains('VAAPISUPPORT', '1', 'va', '', d)} \
    ${@bb.utils.contains('VDPAUSUPPORT', '1', 'vdpau', '', d)} \
"
