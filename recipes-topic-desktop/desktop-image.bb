DESCRIPTION = "TOPIC Miami image for XFCE Graphical Desktop Environment"

# Extending my-image
require recipes-core/images/my-image.bb

# Don't create an ubi image, it won't fit
IMAGE_FSTYPES = "tar.gz"

#MACHINE_FPGA_BITSTREAM = "fpga-image-medical-demo"
MACHINE_FPGA_BITSTREAM = "fpga-image-miami-florida-gen-reference"

# We change the contents of MACHINE_EXTRA_RRECOMMENDS, so override the
# machine dependencies to get the packages we need instead of the
# default ones.
IMAGE_INSTALL_MACHINE_EXTRAS = "${MACHINE_EXTRA_RDEPENDS} ${MACHINE_EXTRA_RRECOMMENDS}"

MY_DEVELOPMENT_EXTRAS = "\
	alsa-utils-aplay alsa-utils-speakertest alsa-utils-amixer alsa-utils-alsactl \
	iw \
	i2c-tools \
	udhcpd-iface-config \
	"

DYPLO = "\
	dyplo-eeprom-license \
	kernel-module-dyplo \
	dyplo-example-app \
	dyplo-utils \
	libdyplo \
"

MY_DRIVERS = "\
	kernel-modules \
	"

MY_XFCE_DESKTOP = "\
	packagegroup-core-x11-xserver \
	packagegroup-xfce-extended \ 
	packagegroup-core-sdk \
	xfce-selector \
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
	" 

MY_THINGS = "\
	modutils-loadscript \
	${MY_DRIVERS} \
	distro-feed-configs \
	tslib-calibrate \
	${MY_DEVELOPMENT_EXTRAS} \
	${MY_XFCE_DESKTOP} \
	"

rootfs_add_xfce_autostart() {
	# Autostart xfce
	echo "gui:5:once:${sysconfdir}/init.d/xfce-selector.sh" >> ${IMAGE_ROOTFS}${sysconfdir}/inittab
}

ROOTFS_POSTPROCESS_COMMAND += "\
	rootfs_add_xfce_autostart; \
	"
