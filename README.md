# Desktop Environment for Florida/Miami. Intended to be built by the server

First, you should clone a copy of topic-platform:
https://github.com/topic-embedded-products/topic-platform
Run the initialisation as usual, then add some extra repo's. Example script to
build the image:

```
git clone http://github.com/topic-embedded-products/topic-platform.git topic-desktop-platform
cd topic-desktop-platform
git submodule update --init
meta-topic/scripts/init-oe.sh
git clone git://repo.topic.nl/meta-topic-desktop meta-topic-desktop
cd meta-topic-desktop
git submodule update
cd ..
git clone git://github.com/OSSystems/meta-browser.git meta-browser
cd meta-browser
git fetch
git checkout origin/morty
cd ..
meta-topic-desktop/scripts/setup-desktop-build.sh
cd build
source ./profile
fpga=xc7z015
export MACHINE=topic-miami-florida-gen-${fpga}
export DTB=uImage-topic-miami-florida-gen.dtb
echo "Building for $MACHINE"
nice bitbake desktop-image
echo "Packaging release for ${fpga}"
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

