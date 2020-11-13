#!/bin/sh

echo "
Please enter the targeted bitrate:
1 - 600 Kbps
2 - 6 Mbps
3 - 60 Mbps
"
read number

if [ "$number" == 1 ]
then
        bitrate=600
elif [ "$number" == 2 ]
then
        bitrate=6000
else
        bitrate=60000
fi

echo "Starting the stream"

gst-launch-1.0 v4l2src device=/dev/video0 ! video/x-raw,width=1920,height=1080,framerate=60/1 ! omxh265enc target-bitrate=${bitrate} ! 'video/x-h265,stream-format=(string)byte-stream' ! h265parse ! matroskamux streamable=true max-cluster-duration=1000 ! tcpserversink host=0.0.0.0 port=5000 &

ip4=$(/sbin/ip -o -4 addr list wlan0 | awk '{print $4}' | cut -d/ -f1)
echo "Please connect to the stream using tcp://${ip4}:5000"

echo "Press a key to exit"

read key
