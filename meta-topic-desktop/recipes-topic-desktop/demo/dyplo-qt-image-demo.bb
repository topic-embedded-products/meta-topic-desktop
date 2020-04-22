SUMMARY = "Dyplo Processing and Partial Reconfiguration Example"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=801f80980d171dd6425610833a22dbe6"
DEPENDS += "libdyplo qtbase"

SRCREV = "d6cb194edb5e04a896fdbf7c0176ff7f18853483"
SRC_URI = "git://github.com/topic-embedded-products/${BPN}.git"

inherit pkgconfig qmake5 gitpkgv

PV = "1+${SRCPV}"
PKGV = "1+${GITPKGV}"

S = "${WORKDIR}/git"

FILES_${PN} += "${datadir}/icons"

# Qt doesn't provide fonts, need to manually add them
RDEPENDS_${PN} += "ttf-dejavu-sans"

do_install() {
	export INSTALL_ROOT=${D}
	make install
	install -d ${D}${datadir}/applications
	install -m 644 ${S}/${BPN}.desktop ${D}${datadir}/applications/
	install -d ${D}${datadir}/icons/hicolor/128x128/apps
	install -m 644 ${S}/${BPN}-icon.png ${D}${datadir}/icons/hicolor/128x128/apps/
}
