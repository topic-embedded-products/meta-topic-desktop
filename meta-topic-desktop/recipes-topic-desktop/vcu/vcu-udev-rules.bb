# Add a udev rule to allow VCU access to "video" users
SUMMARY = "udev rule to allow access to the VCU"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PV = "0"

inherit allarch

SRC_URI = "file://vcu.rules"

S = "${WORKDIR}"

FILES_${PN} = "${nonarch_base_libdir}"

do_compile() {
    true
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/udev/rules.d
    install -m 0644 ${WORKDIR}/vcu.rules ${D}${nonarch_base_libdir}/udev/rules.d/80-vcu.rules
}
