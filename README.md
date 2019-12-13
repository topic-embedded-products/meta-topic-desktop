# Desktop Environment for Florida/Miami.

Run the ```setup-desktop-build.sh``` script to set up OpenEmbedded.
Modify build/conf/local.conf to your liking.

# Build image

To build the desktop image for the given platform, use the following commands:

```
meta-topic-desktop/scripts/setup-desktop-build.sh
cd build
source ./profile
fpga=xc7z015
export MACHINE=topic-miami-florida-gen-${fpga}
nice bitbake desktop-image
```

# Manual installation using tar files

Having built the image, run:

```
export DTB=uImage-topic-miami-florida-gen.dtb
../meta-topic/scripts/create-boot-archive.sh
mv tmp-glibc/deploy/images/${MACHINE}/boot.tar.gz boot_${fpga}.tar.gz
cp tmp-glibc/deploy/images/${MACHINE}/desktop-image-${MACHINE}.rootfs.tar.gz rootfs_desktop-image_${fpga}.tar.gz
```

This delivers two tar files. Extract the "boot" tar onto the primary FAT
partition called "boot" on the SD card, e.g.:
```tar xzf boot_${fpga}.tar.gz -C /media/$USER/boot```

Extract the rootfs tarball to the rootfs partition, you'll need to be root for
that to work:
```sudo tar rootfs_desktop-image_${fpga}.tar.gz -C /media/$USER/rootfs```

# Direct install using WIC image

Insert an SD card, and determine which /dev/ node refers to it. Unmount any
existing partitions. Assuming the SD card is /dev/sdX, the following command
extracts the image to the raw SD device. This will erase everything on the card.

```
gunzip < tmp-glibc/deploy/images/${MACHINE}/desktop-image-${MACHINE}.wic.gz | sudo dd of=/dev/sdX bs=1M
```

# Transfer to eMMC

You can write the eMMC on the ultrascale devices using the WIC image as well,
just write the wic to /dev/mmcblk0 on the board.

Manually, partition and format the eMMC using these commands:

```
# Create a new partition table
parted -s /dev/mmcblk0 mktable msdos \
 mkpart primary fat16 1M 64M \
 mkpart primary ext4 64M 4096M \
 mkpart primary ext4 4096M 100%
# format the DOS part
mkfs.vfat -n "boot" /dev/mmcblk0p1
# Format the Linux rootfs part
mkfs.ext4 -m 0 -L "rootfs" -O sparse_super,dir_index  /dev/mmcblk0p2
# Format the Linux data part, optimize for large files
mkfs.ext4 -m 0 -L "data" -O large_file,sparse_super,dir_index /dev/mmcblk0p3
```

Copy the 'boot' files from SD card's "boot" partition to the boot partition
of the eMMC, and then extract the rootfs tarball on to the "rootfs" partition,
for example using a ssh link:
cat tmp-glibc/deploy/images/tdkzu9/desktop-image-tdkzu9.rootfs.tar.gz | ssh tdkzu9 tar xzf - -C /run/media/root/rootfs
