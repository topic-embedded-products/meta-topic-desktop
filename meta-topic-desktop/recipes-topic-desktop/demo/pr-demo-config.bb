SUMMARY = "PR Demo floorplan layout configuration"
LICENSE = "CLOSED"
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
