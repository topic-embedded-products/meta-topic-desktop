IMAGE=desktop-image
MACHINE=xdpzu7
# Make the SD card work by using an alternative devicetree.
ROOTFS_FIXUP_COMMANDS="ln -f -s devicetree/zynqmp-topic-miamimp-xilinx-xdp-sd-usbhost.dtb boot/system.dtb"
source `dirname $0`/install_to_sd_common.sh
