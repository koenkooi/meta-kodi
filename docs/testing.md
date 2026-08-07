# Boot-testing kodi-image under qemu

## The harness

Two scripts in the sibling `meta-angstrom` layer,
`scripts/qemu-kodi-inspect.sh` and `scripts/qemu-kodi-log-grab.sh`, drive a
`kodi-image` boot under `runqemu ... nographic slirp` via the serial console,
feeding commands on a timer (no interactive TTY needed) and capturing the
output. Both are meant to be run through `kas-container shell` (they need the
real bitbake/runqemu environment), with the project's mandatory
`--skip repos_checkout --skip repos_apply_patches` pair:

```sh
kas-container shell --skip repos_checkout --skip repos_apply_patches \
  meta-angstrom/kas/angstrom.yml:meta-angstrom/kas/qemuarm64.yml:meta-angstrom/kas/macos-local.yml \
  -c "sh /work/meta-angstrom/scripts/qemu-kodi-inspect.sh /build/deploy/images/qemuarm64/kodi-image-qemuarm64.rootfs.ext4"
```

(Path note: inside the shell, cwd is `/build`, and this project's checkout is
mounted at `/work/meta-angstrom` -- not a host-relative path.)

- **`qemu-kodi-inspect.sh`** -- boots, waits ~7 minutes, logs in, runs
  `systemctl is-active weston.service kodi.service graphical.target`,
  `pgrep -a weston`/`pgrep -a kodi`, tails the last 100 lines of `kodi.log`,
  greps `journalctl` for kodi/egl/gles/drm/virtio-gpu lines, then powers off.
  Good for a quick "did it come up" check.
- **`qemu-kodi-log-grab.sh`** -- boots, waits longer (~90s after login for Kodi
  to fully settle), stops `weston.service` cleanly, then `cat`s the *entire*
  `kodi.log` between `===KODI-LOG-BEGIN===`/`===KODI-LOG-END===` markers before
  powering off. Use this one when you actually need to read Kodi's own startup
  sequence, not just confirm a process exists.

qemuarm64 is the only machine actually verified this way so far -- it's the
only one with a proven kodi build, and `runqemu` appends
`qemuarm64.conf`'s `QB_GRAPHICS = "-device virtio-gpu-pci"` unconditionally
(even under `nographic`), so the DRM node Weston needs is present without any
extra flags.

## What "verified working" actually means here

A full boot-to-render pass succeeded, not just "the build completed":

- `weston.service` active, `graphical.target` reached
- `kodi-standalone`/`kodi`/`kodi.bin` processes alive and stable for the whole
  ~9-minute session (no crash, no restart loop)
- Weston's own EGL/DRM init clean: `virtio_gpu` driver loaded, DRM atomic
  modesetting, EGL 1.5 via Mesa, a valid EGL config chosen
- Kodi's *own* windowing also came up correctly:
  `CApplication::CreateGUI - using the wayland windowing system`,
  `GL_RENDERER = llvmpipe (LLVM ..., 128 bits)` /
  `GL_VERSION = OpenGL ES 3.2 Mesa ...` (software rasterizer, expected under
  qemu with no GPU passthrough -- not a bug)
- The Estuary skin loaded fully, through `Home.xml` -- a real, rendered UI, not
  just a process that didn't crash

Audio works end-to-end. Two layered fixes were needed, both in meta-angstrom
not this layer: a qemu-only kernel config fragment (`CONFIG_SND_VIRTIO=y`
etc) plus a `virtio-sound-pci` qemu device get the kernel to register a real
ALSA card, but Kodi still fails to open it on its own -- `weston-init.bb`
(oe-core) never puts the `weston` user in the `audio` group, so the device
exists and works at the system level (`aplay -l`, `/dev/snd/pcmC0D0p` all
correct) but Kodi gets permission denied opening it. A `weston-init.bbappend`
adding that group membership closes the gap. Confirmed via a real boot:
`CAESinkALSA::Initialize - Opened device "sysdefault:CARD=SoundCard"`, zero
`no sink was returned` occurrences.

## Re-running this after a change

1. Build: `kas-container build --skip repos_checkout --skip repos_apply_patches
   meta-angstrom/kas/angstrom.yml:meta-angstrom/kas/qemuarm64.yml:meta-angstrom/kas/macos-local.yml
   --target kodi-image`
2. Boot-test with `qemu-kodi-inspect.sh` first (cheaper, ~15 min budget) --
   confirms weston/kodi/graphical.target are still healthy.
3. Only reach for `qemu-kodi-log-grab.sh` (~18 min budget) when you need the
   full startup log -- e.g. after a windowing, skin, or addon change where a
   process-alive check alone wouldn't catch a silent regression.
4. Report duration + sstate reuse % for the build step, per this project's
   general build-reporting convention -- see the main meta-angstrom TODO/
   mackas-angstrom skill for that convention's exact shape.
