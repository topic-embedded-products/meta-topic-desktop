DESCRIPTION = "Allow anyone to power off the board"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit allarch

SRC_URI = "file://50-poweroff.rules"

FILES_${PN} = "${sysconfdir}/polkit-1/rules.d"

do_install() {
    install -d ${D}${sysconfdir}/polkit-1/rules.d
    install -m 0644 ${WORKDIR}/50-poweroff.rules ${D}${sysconfdir}/polkit-1/rules.d
}

do_compile() {
    true
}
