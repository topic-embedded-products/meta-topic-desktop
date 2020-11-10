#!/bin/sh

yavta -w '0x0098c981 4' /dev/v4l-subdev2 &> /dev/null

#SONY IMX274 Sensor
media-ctl -d /dev/media0 -V "\"IMX274\":0  [fmt:SRGGB10_1X10/1920x1080 field:none]" &> /dev/null
#MIPI CSI2-Rx Subsystem
media-ctl -d /dev/media0 -V "\"a0300000.mipi_csi2_rx_subsystem\":1  [fmt:SRGGB10_1X10/1920x1080 field:none]" &> /dev/null
media-ctl -d /dev/media0 -V "\"a0300000.mipi_csi2_rx_subsystem\":0  [fmt:SRGGB8_1X8/1920x1080 field:none]" &> /dev/null
#Demosaic IP
media-ctl -d /dev/media0 -V "\"a0140000.v_demosaic\":0  [fmt:SRGGB8_1X8/1920x1080 field:none]" &> /dev/null
media-ctl -d /dev/media0 -V "\"a0140000.v_demosaic\":1  [fmt:RBG888_1X24/1920x1080 field:none]" &> /dev/null

media-ctl -d /dev/media0 -V "\"a0100000.v_proc_ss_csc\":0  [fmt:RBG888_1X24/1920x1080 field:none]" &> /dev/null
media-ctl -d /dev/media0 -V "\"a0100000.v_proc_ss_csc\":1  [fmt:VYYUYY8_1X24/1920x1080 field:none]" &> /dev/null

yavta -w '0x0098c9a1 80' /dev/v4l-subdev0 &> /dev/null
yavta -w '0x0098c9a2 55' /dev/v4l-subdev0 &> /dev/null
yavta -w '0x0098c9a3 35' /dev/v4l-subdev0 &> /dev/null
yavta -w '0x0098c9a4 24' /dev/v4l-subdev0 &> /dev/null
yavta -w '0x0098c9a5 40' /dev/v4l-subdev0 &> /dev/null

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
