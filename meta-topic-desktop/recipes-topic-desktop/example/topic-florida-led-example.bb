SUMMARY = "Example application to control the LEDs on a Florida board"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${BPN}.c;endline=5;md5=bce0b4ae87c61ed7c5405899b346df2e"

inherit gitpkgv

SRC_URI = "git://github.com/topic-embedded-products/${BPN}"
SRCREV = "6c16c418fb0ee7d9dbf7e608a33e753bce3a3af5"

S = "${WORKDIR}/git"
PV = "0+${SRCPV}"
PKGV = "0+${GITPKGV}"

PACKAGES += "${PN}-src"

FILES_${PN}-src = "/home/root/${BPN}"

do_install() {
	install -d ${D}/${bindir}
	install -m 755 ${B}/${BPN} ${D}/${bindir}
	install -d ${D}/home/root/${BPN}
	install -m 644 ${S}/*.c ${S}/Makefile ${D}/home/root/${BPN}
}
