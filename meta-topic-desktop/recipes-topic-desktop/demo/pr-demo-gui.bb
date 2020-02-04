SUMMARY = "PR Demo Qt4 GUI"
LICENSE = "CLOSED"
DEPENDS = "libdyplo"

SRCREV = "55a3e604bae90718ab8265fb3832b58989ad7d80"

inherit qt4x11 gitpkgv

PV = "0+${SRCPV}"
PKGV = "0+${GITPKGV}"

# RRECOMMENDS_${PN} += "fpga-image-pr-demo"
RRECOMMENDS_${PN} += "pr-demo-config"

S = "${WORKDIR}/git"

REDMINE_URI ?= "git://repo.topic.nl"
SRC_URI = "${REDMINE_URI}/${BPN}"

SRC_URI_append_zynqmp = " file://0001-Use-streaming-DMA-mode-instead-of-coherent.patch"


FILES_${PN} += "${datadir}/icons"

do_install() {
	export INSTALL_ROOT=${D}
	make install
	install -d ${D}${datadir}/applications
	install -m 644 ${S}/pr-demo.desktop ${D}${datadir}/applications/
	install -d ${D}${datadir}/icons/hicolor/128x128/apps
	install -m 644 pr-demo-icon.png ${D}${datadir}/icons/hicolor/128x128/apps/
}
