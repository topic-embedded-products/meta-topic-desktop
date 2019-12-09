LICENSE = "CLOSED"
SUMMARY = "Tweaks for Topic Desktop"
PV = "0"
SRC_URI = "file://xfce4-power-manager.xml"

inherit allarch

S = "${WORKDIR}"

do_compile() {
	true
}

do_install() {
	install -d ${D}${sysconfdir}/xdg/xfce4/xfconf/xfce-perchannel-xml
	install -m 644 ${S}/xfce4-power-manager.xml ${D}${sysconfdir}/xdg/xfce4/xfconf/xfce-perchannel-xml/
}
