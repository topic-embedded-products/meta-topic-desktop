DESCRIPTION = "Script to initialize and start XFCE for Miami"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${METATOPIC_DESKTOP_BASE}/COPYING;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI = "file://init"

FILES_${PN}-debug += "${bindir}/.debug"

FILES_${PN} += "${datadir} ${sysconfdir}/init.d"

do_install() {
	export INSTALL_ROOT=${D}
	install -d ${D}/${sysconfdir}/init.d
	install -m 755 ${WORKDIR}/init ${D}/${sysconfdir}/init.d/${PN}.sh
}
