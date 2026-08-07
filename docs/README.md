# meta-kodi docs

Working knowledge accumulated while getting Kodi 22 ("Piers") building, booting,
and rendering on this project, plus a structured comparison against LibreELEC
(the most widely-deployed Kodi-focused embedded distro) for what's worth
adopting from there. Treat anything version-pinned (SRCREVs, tags, line
numbers) as a snapshot, not a promise -- verify against the live recipes
before relying on a specific value.

- **[architecture.md](architecture.md)** -- how the kodi/kodi-weston recipes and
  kodi-image actually fit together: the build system, the versioning scheme and
  its one real gotcha, the Wayland/Weston windowing path, and the addon-dev SDK
  mechanism kodi itself exports for other recipes to build against.
- **[addons.md](addons.md)** -- the binary-addon build mechanism
  (kodi-addon.bbclass), what's been ported so far (kodi-platform, pvr.hts), and
  the still-open activation gap (addons install disabled, nothing currently
  turns them on).
- **[testing.md](testing.md)** -- the qemu boot-test harness, what "verified
  working" actually means here, and how to re-run the same verification after a
  change.
- **[libreelec-integration-menu.md](libreelec-integration-menu.md)** -- a
  feature-by-feature comparison against LibreELEC (buildroot-based, not
  bitbake/kas -- nothing here is copy-pasteable, it's a menu of ideas with real
  effort estimates), covering addon distribution, Kodi patches, extra apps,
  in-Kodi settings UI, and build-time configuration. Written to be picked from
  selectively, not taken as one plan.

## Quick orientation

This is an OpenEmbedded/bitbake layer (`meta-kodi`, built via kas), not a
standalone distro -- it lives inside the larger meta-angstrom project
(oe-core + meta-openembedded + kas). The actual bootable image
(`kodi-image.bb`) lives in the sibling `meta-angstrom` layer, not here --
`meta-kodi` only provides the media-center software itself.

```
meta-kodi/
  recipes-multimedia/kodi/          kodi, kodi-weston, kodi-platform, shairplay, libudfread
  recipes-multimedia/kodi-addons/   binary Kodi addons (kodi-addon.bbclass consumers)
  recipes-support/                  crossguid (bbappend), fstrcmp
  classes/                          kodi-common.bbclass, kodi-addon.bbclass
  docs/                             this directory
```
