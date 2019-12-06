SUMMARY = "PR Demo Qt4 GUI"
LICENSE = "CLOSED"
DEPENDS = "libdyplo"

SRCREV = "fd249c1342739f97950282a93dc96a7636d97570"

inherit qt4x11 gitpkgv

PV = "0+${SRCPV}"
PKGV = "0+${GITPKGV}"

# Need font
RDEPENDS_${PN} += "\
	qt4-embedded-fonts-ttf-dejavu \
	"
# RRECOMMENDS_${PN} += "fpga-image-pr-demo"

S = "${WORKDIR}/git"

REDMINE_URI ?= "git://repo.topic.nl"
REDMINE_URI = "git://${HOME}/projects"
SRC_URI = "${REDMINE_URI}/${BPN}"

do_install() {
	export INSTALL_ROOT=${D}
	make install
}
