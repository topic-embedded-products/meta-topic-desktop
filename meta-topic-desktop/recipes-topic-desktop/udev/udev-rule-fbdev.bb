SUMMARY = "Make sure fblcd is the LCD screen"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PV = "0"
S = "${WORKDIR}"

do_compile() {
cat > ${B}/10-fbdev.rules << EOF
KERNEL=="fb[0-9]*", ATTR{name}=="vdma-fb", ACTION=="add", SYMLINK+="fblcd"
EOF
cat > ${B}/99-fbdev.conf << EOF
Section "Device"
  Identifier "fblcd"
  Driver "fbdev"
  Option "fbdev" "/dev/fblcd"
EndSection
EOF
}

FILES_${PN} = "${sysconfdir} ${datadir}"

do_install() {
	install -d ${D}${sysconfdir}/udev/rules.d
	install -m 644 ${B}/10-fbdev.rules ${D}${sysconfdir}/udev/rules.d/
	install -d ${D}${datadir}/X11/xorg.conf.d
	install -m 644 ${B}/99-fbdev.conf ${D}${datadir}/X11/xorg.conf.d
}
