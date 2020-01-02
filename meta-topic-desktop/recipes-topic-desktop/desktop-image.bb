DESCRIPTION = "TOPIC Miami image for XFCE Graphical Desktop Environment"

# Extending my-image
require recipes-core/images/my-image.bb

IMAGE_FEATURES += "x11-base"

# Don't create an ubi image, it won't fit
IMAGE_FSTYPES = "tar.gz wic"

MY_DEVELOPMENT_EXTRAS = "\
	alsa-utils-aplay alsa-utils-speakertest alsa-utils-amixer alsa-utils-alsactl \
	udhcpd-iface-config \
	parted e2fsprogs-mke2fs dosfstools \
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

# not using packagegroup-xfce-extended because it installs xfce-polkit
MY_XFCE_DESKTOP = "\
	avahi-daemon \
	packagegroup-core-x11-xserver \
	packagegroup-xfce-base \
    \
    xfwm4-theme-daloa \
    \
    xfce-dusk-gtk3 \
    \
    xfce4-cpufreq-plugin \
    xfce4-cpugraph-plugin \
    xfce4-datetime-plugin \
    xfce4-eyes-plugin \
    xfce4-clipman-plugin \
    xfce4-diskperf-plugin \
    xfce4-netload-plugin \
    xfce4-genmon-plugin \
    xfce4-xkb-plugin \
    xfce4-wavelan-plugin \
    xfce4-places-plugin \
    xfce4-systemload-plugin \
    xfce4-time-out-plugin \
    xfce4-timer-plugin \
    xfce4-embed-plugin \
    xfce4-weather-plugin \
    xfce4-fsguard-plugin \
    xfce4-battery-plugin \
    xfce4-mount-plugin \
    xfce4-powermanager-plugin \
    xfce4-closebutton-plugin \
    xfce4-equake-plugin \
    xfce4-notes-plugin \
    xfce4-whiskermenu-plugin \
    xfce4-mailwatch-plugin \
    xfce4-kbdleds-plugin \
    xfce4-smartbookmark-plugin \
    xfce4-hotcorner-plugin \
    ${@bb.utils.contains('DISTRO_FEATURES', 'pulseaudio', 'xfce4-pulseaudio-plugin', '', d)} \
    xfce4-sensors-plugin \
    xfce4-calculator-plugin \
    xfce4-verve-plugin \
    \
    ${@bb.utils.contains("DISTRO_FEATURES", "bluetooth", "blueman", "", d)} \
    \
    thunar-media-tags-plugin \
    thunar-archive-plugin \
    \
    xfce4-appfinder \
    xfce4-screenshooter \
    xfce4-power-manager \
    ristretto \
    xfce4-taskmanager \
    gigolo \
    mousepad \
    catfish \
    xfce4-panel-profiles \
    \
	xserver-xf86-config \
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
	xfce-topic-desktop-tweaks \
	pr-demo-gui \
	lxdm \
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
	xf86-input-tslib \
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

# Postprocessing steps
desktopimage_rootfs_postprocess() {
	# Set up timeserver config for CA
	cat > ${IMAGE_ROOTFS}/etc/systemd/timesyncd.conf << EOF
[Time]
NTP=pool.ntp.org
FallbackNTP=time1.google.com time2.google.com time3.google.com time4.google.com
EOF
	# Remove some unwanted auto-start items
	rm -f \
		${IMAGE_ROOTFS}/etc/xdg/autostart/xfce4-notes-autostart.desktop \
		${IMAGE_ROOTFS}/etc/xdg/autostart/xinput_calibrator.desktop \
		${IMAGE_ROOTFS}/etc/xdg/autostart/blueman.desktop
}

ROOTFS_POSTPROCESS_COMMAND += "desktopimage_rootfs_postprocess;"
