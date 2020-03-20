LICENSE = "CLOSED"

inherit gitpkgv

PV = "0+git${SRCPV}"
PKGV = "0.${GITPKGV}"
S = "${WORKDIR}/git"

SRCREV = "77044f5a4d89f28d120bfbfe4ccbb23f8dc38fef"

REPO_TOPIC_PRODUCTS_SERVER ?= "repo-products.topic.nl"
REPO_TOPIC_PRODUCTS_SERVER[vardepvalue] := "repo-products.topic.nl"
SRC_URI = "git://${REPO_TOPIC_PRODUCTS_SERVER}/xdpsensors"
