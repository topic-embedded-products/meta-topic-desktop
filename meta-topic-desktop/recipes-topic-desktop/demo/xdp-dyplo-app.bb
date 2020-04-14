DESCRIPTION = "XDP Dyplo application"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=801f80980d171dd6425610833a22dbe6"

SRCREV = "890c166b2f7114f32c69278e50430225ab0556fd"

DEPENDS += "libdyplo"

inherit autotools pkgconfig gitpkgv

PV = "0+${SRCPV}"
PKGV = "0+${GITPKGV}"
S = "${WORKDIR}/git"

REPO_TOPIC_SERVER ?= "repo.topic.nl"
SRC_URI = "git://${REPO_TOPIC_SERVER}/${BPN}"

# Have some fun with this using:
# export DISPLAY=:0
# dyploroute 0.0-2.0
# xdp-dyplo-app -c 2 -k 2 -w 960 -h 540 -f - | gst-launch-1.0 fdsrc fd=0 blocksize=2073600 do-timestamp=true ! rawvideoparse use-sink-caps=false width=960 height=540 format=bgrx framerate=20/1 ! videoconvert ! ximagesink &
# xdp-dyplo-app -c 1 -k 3 -w 1920 -h 1080 -f - | gst-launch-1.0 fdsrc fd=0 blocksize=8294400 do-timestamp=true ! rawvideoparse use-sink-caps=false width=1920 height=1080 format=bgrx framerate=15/1 ! videoconvert ! ximagesink
