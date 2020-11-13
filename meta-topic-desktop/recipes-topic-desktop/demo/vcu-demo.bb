SUMMARY = "VCU-demo script"
LICENSE = "CLOSED"

inherit allarch

PV = "4"
S = "${WORKDIR}"

SRC_URI = "file://${BPN}.sh file://vcu-demo.desktop"

RDEPENDS_${PN} = "\
	kernel-module-vcu \
	vcu-udev-rules \
	libomxil-xlnx \
	gstreamer1.0-omx \
	libvcu-xlnx \
	vcu-firmware \
	gstreamer1.0-plugins-base-rawparse \
	gstreamer1.0-plugins-base-tcp \
	gstreamer1.0-plugins-base-videoconvert \
	gstreamer1.0-plugins-base-videotestsrc \
	gstreamer1.0-plugins-base-ximagesink \
	gstreamer1.0-plugins-bad-legacyrawparse \
	gstreamer1.0-plugins-bad-videoparsersbad \
	gstreamer1.0-plugins-good-matroska \
	gstreamer1.0-plugins-good-video4linux2 \
"

do_install() {
	install -d ${D}${bindir}
	install -m 755 ${S}/${BPN}.sh ${D}${bindir}
	install -d ${D}${datadir}/applications
	install -m 644 ${S}/vcu-demo.desktop ${D}${datadir}/applications/
}
