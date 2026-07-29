How to Report a Potential Vulnerability
=======================================

meta-kodi is an independent, third-party OpenEmbedded application layer. It
is not part of the Yocto Project's own security process, so please do not
report issues found in this layer's recipes or metadata through the Yocto
Project Security Bugzilla.

If you find a security issue in the recipes, patches, or configuration
carried in this layer (as opposed to in Kodi itself or in one of its
dependencies), please report it privately by emailing the maintainer:

    Koen Kooi <koen@dominion.thruhere.net>

Please include as much detail as possible: the affected recipe and its
version, the layer branch (e.g. `wrynose`), and, if available, example code
or a reproducer.

Issues in Kodi itself
----------------------

Vulnerabilities in Kodi's own source code should be reported upstream
through the Kodi project, not through this layer. See
https://kodi.tv/security or https://github.com/xbmc/xbmc/security for their
current reporting process.

Issues in openembedded-core or meta-openembedded
-------------------------------------------------

Vulnerabilities in recipes this layer depends on but does not itself carry
(openembedded-core, meta-openembedded) should be reported through the Yocto
Project's own process; see
[Security Bugzilla](https://bugzilla.yoctoproject.org/enter_bug.cgi?product=Security)
or email security AT yoctoproject DOT org.
