SUMMARY = "Calibration file for AD7879 touchscreen on Miami Florida board"
LICENSE = "CLOSED"

inherit allarch

SRC_URI = "file://pointercal.xinput"

FILES_${PN} = "${sysconfdir}"

S = "${WORKDIR}"

do_compile() {
    true
}

do_install() {
    install -d ${D}${sysconfdir}
    install -m 644 ${S}/pointercal.xinput ${D}${sysconfdir}
}
