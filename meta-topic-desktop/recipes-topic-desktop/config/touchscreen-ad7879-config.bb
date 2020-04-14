SUMMARY = "Calibration file for AD7879 touchscreen on Miami Florida board"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

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
