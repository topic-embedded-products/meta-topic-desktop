SUMMARY = "PR Demo Qt4 GUI"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=801f80980d171dd6425610833a22dbe6"
DEPENDS = "libdyplo"

SRCREV = "8a14f524266b52df28e6f6a173509aed25cd9e4a"

inherit qt4x11 gitpkgv

PV = "0+${SRCPV}"
PKGV = "0+${GITPKGV}"

RRECOMMENDS_${PN} += "pr-demo-config"

S = "${WORKDIR}/git"

SRC_URI = "git://github.com/topic-embedded-products/${BPN}.git"

FILES_${PN} += "${datadir}/icons"

do_install() {
	export INSTALL_ROOT=${D}
	make install
	install -d ${D}${datadir}/applications
	install -m 644 ${S}/pr-demo.desktop ${D}${datadir}/applications/
	install -d ${D}${datadir}/icons/hicolor/128x128/apps
	install -m 644 pr-demo-icon.png ${D}${datadir}/icons/hicolor/128x128/apps/
}
