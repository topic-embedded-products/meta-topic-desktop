SUMMARY = "Tweaks to disable WiFi powersaving in NetworkManager"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PV = "0"
SRC_URI = "file://wifi-nopowersave.conf"

inherit allarch

S = "${WORKDIR}"

do_compile() {
	true
}

do_install() {
	install -d ${D}${sysconfdir}/NetworkManager/conf.d
	for f in ${S}/*.conf
	do
		install -m 644 $f ${D}${sysconfdir}/NetworkManager/conf.d
	done
}
