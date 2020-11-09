SUMMARY = "Application to test whether all partial nodes function correctly"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=801f80980d171dd6425610833a22dbe6"
DEPENDS += "libdyplo"

SRCREV = "d07be63d316f02500503971804fcb944a78b5fac"
SRC_URI = "git://git@bitbucket.topic.nl:7999/dyp/${BPN}.git;protocol=ssh"

inherit pkgconfig cmake gitpkgv

PV = "0+${SRCPV}"
PKGV = "0+${GITPKGV}"

S = "${WORKDIR}/git"
