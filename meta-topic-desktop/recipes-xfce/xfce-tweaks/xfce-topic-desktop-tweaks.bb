LICENSE = "CLOSED"
SUMMARY = "Tweaks for Topic Desktop"
PV = "0"
SRC_URI = "file://xfce4-power-manager.xml"

inherit allarch useradd

# There's trouble when running XFCE as root user, so create a non-root user
# in this recipe called "user"
USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "-u 1000 -d /home/user -s /bin/sh -p '' -G users,video,dialout,disk,sudo -c 'User' user"

S = "${WORKDIR}"

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
