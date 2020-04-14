SUMMARY = "PR Demo floorplan layout configuration"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
# Outcome depends on machine
PACKAGE_ARCH = "${MACHINE_ARCH}"
# We don't need libc and postprocessing things
INHIBIT_DEFAULT_DEPS = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
EXCLUDE_FROM_SHLIBS = "1"
# Allow creating an empty package if the machine isn't supported (yet) to satisfy dependencies
ALLOW_EMPTY_${PN} = "1"

SRCREV = "f5410d41957e3cf99a39fc9019178a8a012cb4d0"

inherit gitpkgv

PV = "0+${SRCPV}"
PKGV = "0+${GITPKGV}"
S = "${WORKDIR}/git"

REDMINE_URI ?= "git://repo.topic.nl"
SRC_URI = "${REDMINE_URI}/${BPN}"

FILES_${PN} = "${datadir}"

# The pr-demo-partials packages only exists for certain boards. Just adding it
# to RRECOMMENDS will break the build for board that don't provide it, since
# OE will still try to find a provider and bail out. Simplest solution is to
# only add the dependency for certain boards.
PRPARTIALS = ""
PRPARTIALS_tdkz30 = "pr-demo-partials"
PRPARTIALS_tdkzu9 = "pr-demo-partials"

RRECOMMENDS_${PN} = "${PRPARTIALS}"

do_compile() {
    true
}

do_install() {
    install -d ${D}${datadir}
    if [ -d ${S}/${BPN}-${MACHINE} ]
    then
       install -m 0644 ${S}/${BPN}-${MACHINE}/* ${D}${datadir}/
    else
       echo "No configuration for machine ${MACHINE}"
    fi
}
