#!/bin/sh
set -e
if [ ! -z "$1" ]
then
  BUILDDIR="$1"
else
  BUILDDIR=build
fi

if [ ! -d oe-core ]
then
	echo "Please run this as 'meta-topic-desktop/scripts/setup-desktop-build.sh' from the top directory."
	exit 1
fi

if [ ! -d ${BUILDDIR}/conf ]
then
	mkdir -p ${BUILDDIR}/conf
fi

cp -rp meta-topic-desktop/scripts/templates/build/* ${BUILDDIR}/

echo "Build configuration has been overwritten (profile, conf/bblayers.conf, conf/local.conf)." 