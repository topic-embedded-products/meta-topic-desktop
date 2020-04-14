SUMMARY = "Tweaks for Topic Desktop"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PV = "2"
SRC_URI = "file://xfce4-power-manager.xml"

# Creates the "network" group
DEPENDS += "polkit-group-rule-network"

inherit allarch useradd

# There's trouble when running XFCE as root user, so create a non-root user
# in this recipe called "user"
USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "-u 1000 -d /home/user -s /bin/sh -p '' -G users,video,dialout,disk,network,sudo -c 'User' user"

S = "${WORKDIR}"

# Allow our user to mount various media devices, configure networking and power off the board
RRECOMMENDS_${PN} += "polkit-group-rule-usermount polkit-group-rule-network polkit-rule-poweroff"

do_compile() {
	true
}

do_install() {
	install -d ${D}${sysconfdir}/xdg/xfce4/xfconf/xfce-perchannel-xml
	for f in ${S}/*.xml
	do
		install -m 644 $f ${D}${sysconfdir}/xdg/xfce4/xfconf/xfce-perchannel-xml/
	done
}
