SUMMARY = "FPGA bitstream for Topic Medical Demo"
XILINX_VIVADO_VERSION = "2014.4"
require recipes-bsp/fpga/fpga-image.inc

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${METATOPIC_DESKTOP_BASE}/COPYING;md5=751419260aa954499f7abaabaa882bbe"

# The design only makes sense on Florida "gen" and "med" boards
COMPATIBLE_MACHINE = "topic-miami-florida-(med|gen)"

REDMINE_URI ?= "git://repo.topic.nl"
BOARD_DESIGN_URI = "${REDMINE_URI}/${PN}"
BOARD_DESIGN_PATH = ""

inherit gitpkgv
# Note: If you change the SRCREV here, you should also update all the
# fpga-image-*-filters recipe versions which depend on this.
SRCREV = "c49e571c5b75544937ee6261ec055c866f72ddec"
SRCREV_xc7z015 = "a0614687ef5d8c1e84c92110304c99663df38b7d"

PV = "0.${SRCPV}"
PKGV = "0.${GITPKGV}"

do_compile_prepend() {
	chmod a+x *.sh
}

FPGA_CHECKPOINT_PATH = "${datadir}/${PN}"
FPGA_CHECKPOINT_IPS = "dyplo"
# Place the static checkpoint into a separate package
PACKAGES =+ "${PN}-checkpoint"
FILES_${PN}-checkpoint = "${FPGA_CHECKPOINT_PATH}"

do_install_append() {
	install -d ${D}${FPGA_CHECKPOINT_PATH}
	install -m 644 ${S}/checkpoints/top_impl_static.dcp ${D}${FPGA_CHECKPOINT_PATH}/
	if [ ! -z "${FPGA_CHECKPOINT_IPS}" ]
	then
		for ip in ${FPGA_CHECKPOINT_IPS}
		do
			cp -r ${S}/IP/$ip ${D}${FPGA_CHECKPOINT_PATH}/
		done
	fi
}
