SUMMARY = "PR Demo Qt4 GUI"
LICENSE = "CLOSED"
DEPENDS = "libdyplo"

SRCREV = "7fc25523d43d51783320a838b7d09c42caa6fdbb"

inherit qt4x11 gitpkgv

PV = "0+${SRCPV}"
PKGV = "0+${GITPKGV}"

# RRECOMMENDS_${PN} += "fpga-image-pr-demo"

S = "${WORKDIR}/git"

REDMINE_URI ?= "git://repo.topic.nl"
SRC_URI = "${REDMINE_URI}/${BPN}"

FILES_${PN} += "${datadir}/icons"

do_install() {
	export INSTALL_ROOT=${D}
	make install
	install -d ${D}${datadir}/applications
	install -m 644 ${S}/pr-demo.desktop ${D}${datadir}/applications/
	install -d ${D}${datadir}/icons/hicolor/128x128/apps
	install -m 644 pr-demo-icon.png ${D}${datadir}/icons/hicolor/128x128/apps/
}
