DESCRIPTION = "TOPIC Miami image for XFCE Graphical Desktop Environment"

# Extending my-image
require recipes-core/images/my-image.bb

IMAGE_FEATURES += "x11-base"

# Don't create an ubi image, it won't fit
IMAGE_FSTYPES = "tar.gz wic.gz"

MY_DEVELOPMENT_EXTRAS = "\
	alsa-utils-aplay alsa-utils-speakertest alsa-utils-amixer alsa-utils-alsactl \
	udhcpd-iface-config \
	parted e2fsprogs-mke2fs dosfstools \
	"

DYPLO = "\
	kernel-module-dyplo \
	dyplo-example-app \
	dyplo-utils \
	"

MY_DRIVERS = "\
	kernel-modules \
	"

MY_XFCE_DESKTOP = "\
	avahi-daemon \
	packagegroup-core-x11-xserver \
	packagegroup-xfce-extended \ 
	xserver-xf86-config \
	xf86-input-tslib \
	xf86-input-mouse \
	xf86-input-keyboard \
	xclock \
	xrdb \
	twm \
	xterm \
	nano \
	xarchiver \
	ristretto \
	evince \  
	geany \
	python3 \
	python3-setuptools \
	python3-pip \
	sudo \
	ca-certificates \
	" 

# Things that a programmer needs, like a compiler and cmake and libraries...
MY_PROGRAMMER_EXTRAS = "\
	packagegroup-core-sdk \
	cmake \
	git \
	gdb \
	libc-dev \
	libdyplo-dev \
	tcl \
	tk \
	tk-lib \
	tk-dev \
	"

BOARD_SPECIFIC_THINGS = ""
BOARD_SPECIFIC_THINGS_topic-miami-florida = "\
	tslib-calibrate \
	udev-rule-fbdev \
	touchscreen-ad7879-config \
	topic-florida-led-example-src \
	"

# Firefox failed to compile on 32-bit, so restrict to zynqmp for now
BOARD_SPECIFIC_THINGS_zynqmp = "\
	xserver-xorg-extension-glx \
	firefox \
	"

MY_THINGS = "\
	${@bb.utils.contains('VIRTUAL-RUNTIME_dev_manager', 'busybox-mdev', 'modutils-loadscript', '', d)} \
	${MY_DRIVERS} \
	distro-feed-configs \
	${MY_DEVELOPMENT_EXTRAS} \
	${MY_XFCE_DESKTOP} \
	${DYPLO} \
	${BOARD_SPECIFIC_THINGS} \
	"

