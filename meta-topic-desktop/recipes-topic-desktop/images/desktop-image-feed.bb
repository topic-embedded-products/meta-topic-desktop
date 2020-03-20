SUMMARY = "Recipe to create a feed in addition to the image"

# Trick: We want to create the package index, and we don't actually
# package anything, so we "inherit" the package indexer recipe.
require recipes-core/meta/package-index.bb

# We want to build the image itself
DEPENDS = "desktop-image desktop-image-swu-emmc"

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
	python3 \
	python3-setuptools \
	python3-pip \
	strace \
	v4l-utils \
	"

DEPENDS += "${OPTIONAL_PACKAGES}"
