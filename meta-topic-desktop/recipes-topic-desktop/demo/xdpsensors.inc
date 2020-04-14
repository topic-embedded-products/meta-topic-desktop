LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=801f80980d171dd6425610833a22dbe6"

inherit gitpkgv

PV = "0+git${SRCPV}"
PKGV = "0.${GITPKGV}"
S = "${WORKDIR}/git"

SRCREV = "3c4ae735b17d192b787dc2d35249fbfb70004e2d"

REPO_TOPIC_PRODUCTS_SERVER ?= "repo-products.topic.nl"
REPO_TOPIC_PRODUCTS_SERVER[vardepvalue] := "repo-products.topic.nl"
SRC_URI = "git://${REPO_TOPIC_PRODUCTS_SERVER}/xdpsensors"
