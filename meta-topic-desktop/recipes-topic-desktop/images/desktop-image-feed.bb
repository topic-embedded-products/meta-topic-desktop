SUMMARY = "Recipe to create a feed in addition to the image"

# Trick: We want to create the package index, and we don't actually
# package anything, so we "inherit" the package indexer recipe.
require recipes-core/meta/package-index.bb

EXTRA_IMAGES ?= ""
# The 7-series boards only have an SD card, so target that
EXTRA_IMAGES_topic-miami = "desktop-image-swu-sd"
# The zynqmp based board usually have eMMC
EXTRA_IMAGES_zynqmp = "desktop-image-swu-emmc"
# We want to build the image itself
DEPENDS = "desktop-image ${EXTRA_IMAGES}"

# For the zu9 we also want the Dyplo/Qt developer and browser image
EXTRA_IMAGES_append_tdkzu9 = " desktop-dyplo-dev-image-swu-emmc browser-desktop-image-swu-emmc"

# For the headless ttpzu9 and tdpzu9
DEPENDS_ttpzu9 = "nix11-image nix11-image-swu-emmc"
DEPENDS_tdpzu9 = "nix11-image nix11-image-swu-emmc"

# List of packages that we want to build but not deploy on target. In alphabetical
# order for easy maintenance...
OPTIONAL_PACKAGES = "\
	alsa-utils \
	ltp \
	firefox \
	gdb \
	git \
	iperf3 \
	ntfs-3g \
	packagegroup-core-sdk \
	pciutils \
	python3 \
	python3-setuptools \
	python3-pip \
	qt5-creator \
	strace \
	v4l-utils \
	"

OPTIONAL_PACKAGES_append_xdpzu7 += "vcu-demo"

DEPENDS += "${OPTIONAL_PACKAGES}"
