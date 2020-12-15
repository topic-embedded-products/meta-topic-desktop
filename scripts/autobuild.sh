#!/bin/sh -e
if [ ! -d build ]
then
  echo "First time build - setting up."
  scripts/setup-desktop-build.sh
fi

git submodule update --recursive --init

echo "Starting build..."
cd build
source ./profile

rm -rf artefacts
mkdir artefacts

for machine in tdkzu9 tdkz30 xdpzu7 tdkz15 tdkzu6 tdkzu15 ttpzu9 tdpzu9
do
    export MACHINE=$machine
    echo "Building for $MACHINE"
    nice bitbake -k desktop-image-feed
    for wicimg in desktop-image nix11-image
    do
        if [ -e tmp-glibc/deploy/images/${MACHINE}/${wicimg}-${MACHINE}.wic ]
        then
            nice xz -4 < tmp-glibc/deploy/images/${MACHINE}/${wicimg}-${MACHINE}.wic > artefacts/${wicimg}-${MACHINE}.wic.xz &
        fi
    done
    cp tmp-glibc/deploy/images/${MACHINE}/*image-swu-*-${MACHINE}.swu artefacts/
done

# Wait for XZ sub-processes to complete
wait
