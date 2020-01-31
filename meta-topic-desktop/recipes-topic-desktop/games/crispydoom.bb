# Copied from https://github.com/geoffrey-vl/meta-doom/
DESCRIPTION = "A Doom Clone based on SDL"
SECTION = "games"
DEPENDS = "virtual/libsdl2 libsdl2-mixer libsdl2-net"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING.md;md5=60d644347832d2dd9534761f6919e2a6"

RRECOMMENDS_${PN} = "freedoom"

PV = "5.6.3"
SRC_URI = "git://github.com/fabiangreffrath/crispy-doom"
SRCREV = "346ad0295645b6e4b877dd6025b80c18b853b822"

inherit autotools-brokensep gettext pkgconfig

S = "${WORKDIR}/git"

FILES_${PN} += "/usr/share"


