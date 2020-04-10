DESCRIPTION = "Image without desktop but with X11"

# Extending my-image
require recipes-core/images/my-image.bb

IMAGE_FSTYPES = "tar.gz"

MY_DEVELOPMENT_EXTRAS = "\
	parted e2fsprogs-mke2fs \
	"

DYPLO = "\
	kernel-module-dyplo \
	dyplo-example-app \
	dyplo-utils \
	dyplo-udev-rules \
	"

MY_DRIVERS = "\
	kernel-modules \
	linux-firmware-rtl8192cu \
	"

# Minimal packages to be able to run X11 applications on a remote Xserver
MY_XFCE_DESKTOP = "\
	avahi-daemon \
	haveged \
	packagegroup-core-x11-utils liberation-fonts \
	pr-demo-gui \
	"

BOARD_SPECIFIC_THINGS = ""
BOARD_SPECIFIC_THINGS_topic-miami-florida = "\
	tslib-calibrate \
	xf86-input-tslib \
	udev-rule-fbdev \
	touchscreen-ad7879-config \
	topic-florida-led-example-src \
	"

BOARD_SPECIFIC_THINGS_zynqmp = "\
	xserver-xorg-extension-glx \
	"

BOARD_SPECIFIC_THINGS_append_xdpzu7 = "xdpsensors-frontend"

MY_THINGS = "\
	kernel-devicetree \
	${@bb.utils.contains('VIRTUAL-RUNTIME_dev_manager', 'busybox-mdev', 'modutils-loadscript', '', d)} \
	${@ 'mtd-utils-ubifs' if d.getVar('UBI_SUPPORT') == 'true' else ''} \
	${@bb.utils.contains("IMAGE_FEATURES", "swupdate", d.getVar('SWUPDATE_THINGS'), "", d)} \
	${MY_DRIVERS} \
	distro-feed-configs \
	${MY_DEVELOPMENT_EXTRAS} \
	${MY_XFCE_DESKTOP} \
	${DYPLO} \
	${BOARD_SPECIFIC_THINGS} \
	"

# Postprocessing steps
desktopimage_rootfs_postprocess() {
	# Set up timeserver config for CA
	cat > ${IMAGE_ROOTFS}/etc/systemd/timesyncd.conf << EOF
[Time]
NTP=pool.ntp.org
FallbackNTP=time1.google.com time2.google.com time3.google.com time4.google.com
EOF
}

ROOTFS_POSTPROCESS_COMMAND += "desktopimage_rootfs_postprocess;"
