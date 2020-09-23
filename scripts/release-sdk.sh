#!/bin/sh -e
echo "Starting SDK build..."

cd build
source ./profile

for machine in tdkzu9 tdkz30
do
	export MACHINE=$machine
	echo "Building SDK for $MACHINE"
	nice bitbake -k -c populate_sdk desktop-image
done

# Update images and SDK on external web server (downloads.topic.nl)
s3cmd put --acl-public --no-progress tmp-glibc/deploy/sdk/*.sh s3://topic-downloads/sdk/topic-desktop/
