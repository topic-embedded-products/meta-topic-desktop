#!/bin/sh -e
# Update images and feeds on external web server (downloads.topic.nl)
cd build
# Update feeds on external web server (downloads.topic.nl)
s3cmd sync --acl-public --no-progress --delete-removed tmp-glibc/deploy/ipk/ s3://topic-downloads/topic-desktop-feed/
# Update images and SDK on external web server (downloads.topic.nl)
s3cmd put --acl-public --no-progress artefacts/* s3://topic-downloads/images/topic-desktop/
