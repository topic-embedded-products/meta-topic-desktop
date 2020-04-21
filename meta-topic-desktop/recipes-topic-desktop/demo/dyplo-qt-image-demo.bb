SUMMARY = "Dyplo Processing and Partial Reconfiguration Example"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=801f80980d171dd6425610833a22dbe6"
DEPENDS = "libdyplo"

SRCREV = "d6cb194edb5e04a896fdbf7c0176ff7f18853483"
SRC_URI = "git://github.com/topic-embedded-products/${BPN}.git"

inherit qt4x11 gitpkgv

PV = "1+${SRCPV}"
PKGV = "1+${GITPKGV}"

S = "${WORKDIR}/git"

FILES_${PN} += "${datadir}/icons"

# Need JPEG support to display JPEG images
RDEPENDS_${PN} += "qt4-plugin-imageformat-jpeg"

do_install() {
	export INSTALL_ROOT=${D}
	make install
	install -d ${D}${datadir}/applications
	install -m 644 ${S}/${BPN}.desktop ${D}${datadir}/applications/
	install -d ${D}${datadir}/icons/hicolor/128x128/apps
	install -m 644 ${BPN}-icon.png ${D}${datadir}/icons/hicolor/128x128/apps/
}
