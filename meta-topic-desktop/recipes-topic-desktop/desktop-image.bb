DESCRIPTION = "TOPIC Miami image for XFCE Graphical Desktop Environment"

# Extending my-image
require recipes-core/images/my-image.bb

IMAGE_FEATURES += "x11-base"

# Don't create an ubi image, it won't fit
IMAGE_FSTYPES = "tar.gz wic.gz"

MY_DEVELOPMENT_EXTRAS = "\
	alsa-utils-aplay alsa-utils-speakertest alsa-utils-amixer alsa-utils-alsactl \
	iw \
	i2c-tools \
	udhcpd-iface-config \
	topic-florida-led-example-src \
	"

DYPLO = "\
	dyplo-eeprom-license \
	kernel-module-dyplo \
	dyplo-example-app \
	dyplo-utils \
	libdyplo-dev \
	"

MY_DRIVERS = "\
	kernel-modules \
	"

MY_XFCE_DESKTOP = "\
	packagegroup-core-x11-xserver \
	packagegroup-xfce-extended \ 
	packagegroup-core-sdk \
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
	florence \
	geany \
	firefox \
	cmake \
	python3 \
	python3-setuptools \
	python3-pip \
	tcl \
	tk \
	tk-lib \
	tk-dev \
	sudo \
	git \
	gdb \
	libc-dev \
	example-wifi-config \
	touchscreen-ad7879-config \
	" 

MY_THINGS = "\
	${@bb.utils.contains('VIRTUAL-RUNTIME_dev_manager', 'busybox-mdev', 'modutils-loadscript', '', d)} \
	${MY_DRIVERS} \
	distro-feed-configs \
	tslib-calibrate \
	${MY_DEVELOPMENT_EXTRAS} \
	${MY_XFCE_DESKTOP} \
	${DYPLO} \
	udev-rule-fbdev \
	"
