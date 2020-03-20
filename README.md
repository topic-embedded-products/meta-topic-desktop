# Desktop Environment for Florida/Miami.

Run the ```setup-desktop-build.sh``` script to set up OpenEmbedded.
Modify build/conf/local.conf to your liking.

# Build image

To build the desktop image for the given platform, use the following commands:

```
meta-topic-desktop/scripts/setup-desktop-build.sh
cd build
source ./profile
export MACHINE=tdkzu6
nice bitbake desktop-image-swu-emmc
```

Fill in the correct MACHINE name if you have one of the other boards.

# Upgrade existing installation in eMMC

Connect the board to a network. Any network will do, USB dongle or WiFi. Power
it up so it runs the previous image (can be desktop or "my" image).

Direct your PC's browser to the board's IP address (or name, by default something
like tdkzu9.local should work fine too) port 8080, e.g.
http://tdkzu9.local:8080/
This should show an SWUpdate screen. Drop the newly built image file called
`desktop-image-swu-emmc-${MACHINE}.swu` onto the upload box, and in a minute or
so the newly installed image should be up and running.

# First time install

If the board hasn't been used before, or if you 'bricked' it somehow, use the
SD card routine to start the image on an SD card.


# Manual installation using tar files

First `bitbake desktop-image`

Insert an SD card and partition it with the `partition_sd_card.sh` script. This
creates 4 partitions on the card. Remove the card and insert it again to have
the system mount the new partitions.

Having built the image, copy the bootloader files onto the first FAT partition
called "boot":
```cp tmp-glibc/deploy/images/${MACHINE}/boot.bin tmp-glibc/deploy/images/${MACHINE}/u-boot.i* /media/$USER/boot```

Extract the rootfs tarball to the rootfs partition, you'll need to be root for
that to work:
```sudo tar rootfs_desktop-image_${fpga}.tar.gz -C /media/$USER/rootfs-a-sd```


# Direct install using WIC image

Insert an SD card, and determine which /dev/ node refers to it. Unmount any
existing partitions. Assuming the SD card is /dev/sdX, the following command
extracts the image to the raw SD device. This will erase everything on the card.

```
gunzip < tmp-glibc/deploy/images/${MACHINE}/desktop-image-${MACHINE}.wic.gz | sudo dd of=/dev/sdX bs=1M
```

This results in a system that cannot be updated using swupdate, so it's not preferred.
It's convenient for quickly bootstrapping a board with eMMC so you can boot with
the SD card and then transfer everything to eMMC.


# Transfer to eMMC (MPSoC modules only)

You can write the eMMC on the ultrascale devices using the WIC image as well,
just write the wic to /dev/mmcblk0 on the board. This results in a system that
cannot be updated using swupdate, so it's not preferred.

To partition the eMMC card, boot from SD card and then run:
```/usr/sbin/partition_sd_card /dev/mmcblk0```

Copy the 'boot' files from SD card's "boot" partition to the boot partition
of the eMMC.

Then update the eMMC image using swupdate as described above.
