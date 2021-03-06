DESCRIPTION = "Allow users of group usermount or disk to mount/umount/eject media"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

require recipes-extended/polkit/polkit-group-rule.inc

SRC_URI = "file://50-usermount.rules"

do_install() {
    install -m 0644 ${WORKDIR}/50-usermount.rules ${D}${sysconfdir}/polkit-1/rules.d
}

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM_${PN} = "--system usermount"
