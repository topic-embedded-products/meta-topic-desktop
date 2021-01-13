# Create and use alternate bootscript that forces 1080p resolution on the xdpzu7
# Makes boot.scr a symlink that can be altered to revert to "auto" behavior

do_compile_xdpzu7 () {
	sed '1 a setenv bootargs $bootargs drm_kms_helper.edid_firmware=HDMI-A-1:edid/1920x1080.bin' ${WORKDIR}/boot.scr > ${S}/boot-1080p.scr
	oe_mkimage_script -n "autorun" -d ${WORKDIR}/boot.scr ${S}/autorun-auto.uimage.scr
	oe_mkimage_script -n "autorun" -d ${S}/boot-1080p.scr ${S}/autorun-1080p.uimage.scr
}

do_install_xdpzu7 () {
	install -d ${D}/boot
	install ${S}/autorun-auto.uimage.scr ${D}/boot/boot-auto.scr
	install ${S}/autorun-1080p.uimage.scr ${D}/boot/boot-1080p.scr
	ln -s -f boot-1080p.scr ${D}/boot/boot.scr
}
