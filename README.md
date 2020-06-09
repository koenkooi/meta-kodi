# Meta-kodi

## Introduction

The official OpenEmbedded/Yocto Project layer for Kodi

The meta-kodi layer depends on:

	URI: git://git.openembedded.org/openembedded-core
	layers: meta

	URI: git://git.openembedded.org/meta-openembedded
	layers: meta-oe, meta-multimedia, meta-networking
	
	URI: https://git.openembedded.org/meta-python2

Please follow the recommended setup procedures of your OE distribution. For Angstrom that is http://www.angstrom-distribution.org/building-angstrom, other distros should have similar online resources.

## Customization

There is a list of variables that can be used in your local.conf or distro.conf to customize behavior

**KODISYSTEMDAUTOSTART** - If your distribution is shipped with systemd support then
*kodi-systemd-service* recipe will be installed automatically by kodi. By default **KODISYSTEMDAUTOSTART**
variable is set to "enable" and this will trigger autostart kodi behavior. It can be changed to "disable" 
in oder to disable autostart feature.

## Init manager

We strongly suggesting to use systemd as init manager. Sysvinit is untested and may not work properly.

## Contributing

Please use github for pull requests: https://github.com/koenkooi/meta-kodi/pulls

## Reporting bugs

The github issue tracker (https://github.com/koenkooi/meta-kodi/issues) is being used to keep track of bugs.

Maintainers: Koen Kooi <koen@dominion.thruhere.net>
