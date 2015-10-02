DESCRIPTION = "Example WPA Wifi configuration"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${METATOPIC_DESKTOP_BASE}/COPYING;md5=751419260aa954499f7abaabaa882bbe"
inherit allarch
# Machine specific
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "4"

RDEPENDS_${PN} = "\
	wireless-tools \
	wpa-supplicant \
	"

SRC_URI = "file://*"
S = "${WORKDIR}"

do_install() {
	install -d ${D}/etc
	install -m 644 ${S}/wpa_example_config.conf ${D}/etc/
}
