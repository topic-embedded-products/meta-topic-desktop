SUMMARY = "Xilinx XDP IIO sensor"

require xdpsensors.inc

inherit allarch setuptools update-rc.d systemd

INITSCRIPT_NAME = "xdpsensors.sh"
INITSCRIPT_PARAMS = "defaults 88"

SYSTEMD_SERVICE_${PN} = "${BPN}.service"

# python-debugger solves missing "pdb" module when starting twisted-web server
# python-logging, python-setuptools, python-pyhamcrest appear to be required for twisted-web
# xdpsensors.sh starts xdp-dyplo-app, and gst-launch with fdsrc, rawvideoparse,
# videoconvert, jpegenc, multifilesink
RDEPENDS_${PN} += "\
	python-core \
	python-twisted-web \
	python-mmap \
	python-debugger \
	python-incremental \
	python-logging \
	python-setuptools \
	python-pyhamcrest \
	libiio-python \
	dyplo-utils \
	xdp-dyplo-app \
	gstreamer1.0-plugins-base-rawparse \
	gstreamer1.0-plugins-base-videoconvert \
	gstreamer1.0-plugins-good-jpeg \
	gstreamer1.0-plugins-good-multifile \
	"

do_install_append() {
	install -d ${D}/var/www
	for f in ${S}/*.jpg ${S}/*.ico ${S}/*.tac
	do
		install -m 644 $f ${D}/var/www/
	done

	# initscripts
	install -d ${D}${sysconfdir}/init.d
	install -m 755 ${S}/xdpsensors.sh ${D}${sysconfdir}/init.d/

	# systemd
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${S}/${BPN}.service ${D}${systemd_unitdir}/system/
	sed -i -e 's,@BINDIR@,${bindir},g' ${D}${systemd_unitdir}/system/${BPN}.*
}
