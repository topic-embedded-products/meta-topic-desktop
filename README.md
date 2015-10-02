# Desktop Environment for Florida/Miami

First, make sure you have followed the README from meta-topic:
https://github.com/topic-embedded-products/meta-topic

Then follow these steps from the my-zynq directory.
```
# Checkout tested revisions of meta-oe, oe-core and meta-browser.
cd meta-oe
git checkout 34dcefb1bfbbac33a771b4b68748e0d9d877365e
cd ..
cd oe-core
git checkout 8c73bb7949656d91f138c087b9d261cdce90a94b
cd ..
git clone git://github.com/OSSystems/meta-browser.git meta-browser
cd meta-browser
git checkout 6ae140b29f0201fe3bb470da8c96c9e142294ebf
cd ..

git clone git://repo.topic.nl/meta-topic-desktop meta-topic-desktop


cd build
# Edit bblayers.conf to add the required layers.
# The BBLAYERS variable should include (at least) these layers:
#   BBLAYERS = " \
#     ${LAYERTOPDIR}/oe-core/meta \
#     ${LAYERTOPDIR}/meta-oe/meta-oe \
#     ${LAYERTOPDIR}/meta-oe/meta-xfce \
#     ${LAYERTOPDIR}/meta-oe/meta-gnome \
#     ${LAYERTOPDIR}/meta-oe/meta-python \
#     ${LAYERTOPDIR}/meta-topic \
#     ${LAYERTOPDIR}/meta-browser \
#     ${LAYERTOPDIR}/meta-topic-desktop \
# "
vi conf/bblayers.conf

# Edit local.conf to build the right distro.
# Change the DISTRO=".." line to:
#   DISTRO="topic-desktop"
vi conf/local.conf

# Then build the desktop image:
. ./profile
bitbake desktop-image
```