# LibreELEC integration menu

A structured comparison against a local clone of LibreELEC
(https://github.com/LibreELEC/LibreELEC.tv, buildroot-based, **not**
bitbake/kas) at `~/oe/work/LibreELEC.tv`, synthesized into a pick-and-choose
menu. Nothing here is copy-pasteable -- buildroot's `package.mk` format is
fundamentally different from bitbake `.bb`/`.bbappend` -- this documents *what*
LibreELEC does and *whether the same feature* is worth building here, with real
effort estimates against this project's actual bitbake/kas mechanisms.

**Headline finding:** only about a quarter of what LibreELEC does is worth
taking. Most of their tree exists to work around constraints (a read-only
`/storage`-overlay rootfs, no console, connman as the network manager, an
appliance-style whole-image OTA updater) that meta-angstrom simply does not
have -- normal writable OE rootfs, systemd-networkd, SSH/console as the
standard admin path. The genuinely valuable finds are one live upstream Kodi
bug fix that lands squarely on this project's Weston path, and LibreELEC's
addon-repository distribution model (as an architectural idea, not literal code).

---

## A. Patches carried against Kodi itself

**A1. Wayland fullscreen-state fix -- DO NOW.** Kodi's Wayland windowing trusts
the *requested* fullscreen state instead of the compositor-*reported* state
from the configure event -- wrong GUI/video resolution info whenever the
compositor overrides the client's request. Confirmed **still unmerged
upstream** at both this project's SRCREV and LibreELEC's newer one. This
project's `weston-kodi.ini` uses `kiosk-shell.so`, which unilaterally
fullscreens its clients -- precisely the scenario this patch exists for.
Effort: one new patch file + one `SRC_URI` line. Verify hunks apply against
our SRCREV before committing (LibreELEC generated theirs against a newer tree).

**A2. SIGTERM/shutdown rework -- LATER (watch item).** Lets systemd's
`SIGTERM`/`TimeoutStopSec` drive a clean shutdown/settings-save instead of a
synchronous stop from the message handler; LibreELEC's `kodi.service` pairs it
with `ExecStop=kill -TERM` + `TimeoutStopSec=30`. This project's unit has no
`ExecStop` at all today. Don't preempt -- revisit only if real-hardware testing
shows Kodi settings not persisting across reboot.

**A3. Lircmap.xml devinput key aliases -- LATER, free when relevant.** Pure
data-file patch adding physical `KEY_*` codes as remote-control aliases. Grab
it the day any machine actually has an IR/devinput remote; no machine does yet.

**A4. chmod+x on addon binaries / service-addon install hook -- rewrite, don't
port.** Real underlying mechanisms, but both hardcode LibreELEC's own
`/storage/.kodi` paths and init scripts. Write fresh against this project's
real `KODIHOME` if/when actually needed.

**A5/A6. Everything else in LibreELEC's numbered patch set -- SKIP.**
Branding/product patches LibreELEC's own devs say won't go upstream (RSS feed,
battery sysinfo, addon-repo allowlist, Estuary settings tile); already solved
differently here (internal TexturePacker disable -- this project already has
`kodi-texturepacker-native`); `/storage`-overlay-specific (udevil, mount
filtering, env refresh); policy opinions not applicable here (`HasInternet()`
always true, Pulse device renaming); a 2016 fix near-certainly already in this
SRCREV; a two-pass host-target build problem this project's
`kodi-addon.bbclass` doesn't have. The `cec-framework/` and `drmprime-filter/`
conditional patch dirs target Rockchip/Allwinner-specific paths (in-kernel CEC
framework, GBM DRM_PRIME filtering) this project doesn't use (libcec already
works, see `addons.md`), and both are marked **WIP by LibreELEC themselves**.

---

## B. Addon packaging & activation

**B1. Confirmed: no LibreELEC prior art for pre-enabling addons.** Full-tree
grep for addon-activation seeding (`guisettings`/`addon_data`/`Addons33`/
`sources.xml`/`userdata`) found nothing. No device's default package list
includes any `pvr.*` addon. The one build-time "always included" flag
(`PKG_IS_ADDON="embedded"`, used only by `peripheral.joystick`) is a compile-set
flag, not a Kodi-DB enabled flag, and no PVR client uses it. This project's own
open "pvr.hts ships disabled" gap has no shortcut to steal from here -- it's
net-new design work either way.

**B2. Local Kodi addon repository ("repository.angstrom") -- DO NOW, top
structural pick.** LibreELEC's actual answer, at a different layer: addons
build into a per-arch zip feed, and the base image ships only a small bootstrap
addon pointing Kodi's addon browser at that feed. Installing from a repo
fetches *and* enables in one step -- the activation problem evaporates because
a disabled addon is never shipped in the first place. Effort: roughly (a) a
small task emitting the addon zip + `addons.xml`/`addons.xml.md5` from what
`kodi-addon.bbclass` already builds, (b) hook into the existing
`scripts/publish-feed.sh`, (c) one small `kodi-repository-angstrom.bb`
installing a short `addon.xml` bootstrap, (d) add it to `kodi-image.bb`. No
Kodi patch needed (unlike LibreELEC, this project doesn't need to be on Kodi's
official-repo allowlist for a locally-installed repo addon to work). Caveat:
needs a reachable feed URL at runtime, so it doesn't cover an offline/unattended
qemu boot test on its own -- see B3.

**B3. Activation for the unattended qemu boot test -- DO NOW, pick one.**
(a) a first-boot systemd oneshot waiting on Kodi's JSON-RPC socket, calling
`Addons.SetAddonEnabled` (needs `jsonrpc` enabled via a seeded
`advancedsettings.xml`); or (b) a pre-generated `userdata/Database/Addons33.db`
committed as a binary seed. **Prefer (a)** -- a binary DB seed keyed to a Kodi
schema version rots silently on the next Kodi bump; JSON-RPC is inspectable
and self-healing.

---

## C. Extra apps/services beyond core Kodi

**C1. oscam -- DO NOW** (already tracked as a separate TODO item, and this
research de-risked it). Softcam/CI conditional-access daemon, talks to
tvheadend over the network -- fits the existing "tvheadend as a separate
service" pattern, not `kodi-addon.bbclass`. LibreELEC's `package.mk` hands over
the real dependency set (`openssl pcsc-lite`) and a concrete default
reader/protocol flag set to start from instead of guessing. Gotcha: GPL-3.0-only,
and upstream is a tarball snapshot from a non-GitHub host -- check license
policy and mirror availability before committing.

**C2. RetroPlayer + libretro cores -- LATER.** Structurally already solved:
LibreELEC wraps each bare libretro core (`libretro-*`, plain Makefile) in a
Kodi binary addon (`game.libretro.*`), exactly the `kodi-platform` +
`kodi-addon.bbclass` pattern already in place here for PVR backends. Fun and a
good board fit, but net-new scope with no current demand -- start with 2-3
light cores (NES/GB/SNES-class) if ever pursued, skip the heavy long tail
(MAME/PSX/Saturn) permanently, they don't fit qemu or the current boards.
LibreELEC does not ship RetroArch itself on ARM (x86-only flatpak) -- don't
chase that.

**C3. Docker -- SKIP for now.** `meta-virtualization` already has mature
recipes; the real remaining work (if ever wanted) is a `kas/docker.yml`
opt-in fragment, not porting LibreELEC's static-binary bundling. Wrong
RAM/storage profile for nearly every current machine.

**C4. Home Assistant -- SKIP, no prior art exists at all** (zero hits
anywhere in the LibreELEC tree -- not a LibreELEC feature to begin with).

**C5. Ambilight (hyperion/hyperhdr) -- SKIP.** Real, well-packaged upstream,
but gated on physical LED-strip hardware nothing in the current lineup has.

**C6. Jellyfin/Emby/MariaDB/Tailscale/Syncthing/Snapcast/MPD -- SKIP**, or
rather: not a meta-kodi decision. General server-appliance features orthogonal
to "Kodi frontend on embedded ARM" -- meta-openembedded already has them where
actually wanted.

---

## D. Settings UI inside Kodi

**D1. Port `service.libreelec.settings` -- SKIP, and don't use it as a
template either.** This is the clearest "don't" on the whole menu. The addon
exists to work around a stock LibreELEC box having *no other administrative
surface* (no getty, no web UI, read-only-ish rootfs, `/storage` the only
writable area) -- every mechanism in it (write a file under
`/storage/.cache/services/`, then `systemctl restart`) is specifically a
workaround for a rootfs that can't be edited in place. meta-angstrom images are
normal writable OE rootfs with working systemd presets and SSH/console as the
standard admin path -- porting this would create a second config surface
shadowing what oe-core/systemd already do correctly. Also a hard blocker on its
own terms: the addon's networking module is a full `net.connman` D-Bus Agent,
and this project's images confirmed on `systemd-networkd`, not connman -- it'd
be a full rewrite, not a port.

**D2. A narrow "join Wi-Fi from the Kodi GUI" addon -- LATER, only on real
demand.** The one capability SSH/console genuinely can't replace, because it's
chicken-and-egg (need the network for a shell, need a shell to configure the
network) -- only matters for a keyboardless kiosk board with no Ethernet.
Doesn't fit `kodi-addon.bbclass` (that's for compiled C++ addons) -- would need
a new, simpler no-compile recipe pattern instead, driven via
`networkctl`/`wpa_cli` against systemd-networkd. Build only when a concrete
headless-Wi-Fi target exists.

---

## E. Build-time configuration

**E1. PROJECT/DEVICE cascade -- SKIP, no gap.** LibreELEC's
`distributions → projects/<PROJECT> → devices/<DEVICE> → config/arch.<ARCH>`
override chain does structurally the same job as `kas/*.yml` fragments layered
over `kas/angstrom.yml`. Nothing superior to import.

**E2. Cross-check PACKAGECONFIG against Kodi's real `ENABLE_*` surface -- DO
NOW, cheapest win on the whole menu.** LibreELEC's `configure_package()`
function is effectively a ready-made checklist of every `-DENABLE_XXX` flag
Kodi 22 actually supports. Two concrete gaps found reading this project's own
recipe against it: **no `bluray` PACKAGECONFIG entry at all** (Kodi's
`ENABLE_BLURAY` may be silently autodetected by CMake from whatever's in the
sysroot -- exactly the non-determinism PACKAGECONFIG exists to prevent), and
`optical`/`samba`/`mariadb`/`plist`/`airtunes` are defined but not in the
default set (possibly deliberate, worth a conscious confirmation rather than
an accident). Also worth mirroring: LibreELEC hard-`die`s on
ALSA/PulseAudio/Pipewire being simultaneously enabled; this project's default
enables `alsa` plus filtered `pipewire`/`pulseaudio`, with no equivalent guard
in the existing `__anonymous()` sanity check. Effort: one file, a handful of
lines.

**E3. Local addon feed -- see B2**, not double-counted here.

**E4. Pre-build config-selection banner -- SKIP, or a 10-minute nicety.**
Mildly nice for build auditability; bitbake already logs most of the same
information.

**E5. Kodi lifecycle unit set -- LATER, largest orphaned finding.** LibreELEC
has a full `kodi.target` + `kodi-{autostart,halt,poweroff,reboot,
waitonnetwork,cleanlogs}.service` set, and `kodi.sh` implements a genuine
crash-loop detector / safe mode (tracks restart timestamps, renames
`~/.kodi` to `~/.kodi.FAILED` after N crashes in M seconds). This project's
current setup -- one `kodi.service` plus a `weston.service.d/kodi.conf`
drop-in -- has none of it. The crash-loop detector has real value on a
headless board that would otherwise boot-loop forever, but it's meaningless
until Kodi runs unattended on real hardware. Park it; pairs naturally with A2
if ever picked up.

---

## If you only pick 3

1. **A1** -- backport the Wayland fullscreen-state patch. Best value-to-effort
   on the entire menu: one file, confirmed still-unmerged upstream, and it
   targets the exact code path (`kiosk-shell.so` fullscreening clients on its
   own authority) this project's Weston setup actually runs.
2. **B2 + B3** -- close the addon-activation gap. The actual open TODO item;
   confirmed there's no shortcut to steal from LibreELEC. Take their
   *architecture* (local addon repository, so install = enable) paired with a
   small JSON-RPC first-boot oneshot for the offline qemu-test path.
3. **E2** -- cross-check PACKAGECONFIG against Kodi's real `ENABLE_*` surface.
   An hour of work, one file; the missing `bluray` entry alone justifies it.

**Honourable mention:** C1 (oscam) is the obvious fourth -- already tracked
separately, and this research turned a guess into a transcription of the real
dependency/flag set. **The clearest "don't":** D1 -- do not port
`service.libreelec.settings`, and do not use it as a structural template; it
solves a problem (no console, read-only rootfs, connman) this project doesn't
have.
