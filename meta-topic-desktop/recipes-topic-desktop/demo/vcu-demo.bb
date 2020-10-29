SUMMARY = "VCU-demo script"
LICENSE = "CLOSED"

PV = "1"
S = "${WORKDIR}"

SRC_URI = "file://${BPN}.sh file://vcu-demo.desktop"

RDEPENDS_${PN} = "kernel-module-vcu \
	libomxil-xlnx \
	gstreamer1.0-omx \
	libvcu-xlnx \
	vcu-firmware \
	gstreamer1.0-plugins-bad \
	gstreamer1.0-plugins-good \
	gstreamer1.0-plugins-base \
"

do_install() {
	install -d ${D}${bindir}
	install -m 755 ${S}/${BPN}.sh ${D}${bindir}
	install -d ${D}${datadir}/applications
	install -m 644 ${S}/vcu-demo.desktop ${D}${datadir}/applications/
}
