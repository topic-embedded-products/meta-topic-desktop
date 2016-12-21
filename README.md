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
