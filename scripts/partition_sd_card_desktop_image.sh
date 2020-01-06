#!/bin/bash -e
if [ "$(id -u)" != "0" ]; then
   echo "This script must be run as root" 1>&2
   exit 1
fi

if [ -z "$1" ] || [ ! -b "$1" ]
then
	echo "Usage: $0 [device]"
	echo "no device found."
	exit 1
else
	DEV=$1
fi

# Function to reverse an array
reverse () {
    local out=()
    while [ $# -gt 0 ]; do
        out=("$1" "${out[@]}")
        shift 1
    done
    echo "${out[@]}"
}

echo "Make sure you are using the correct device, this operation will CLEAR ALL DATA!"
while true; do
	read -p "Are you sure you wish to clear, partition and format ${DEV}? " yn
	case $yn in
		[Yy]* ) break;;
		[Nn]* ) exit;;
		* ) echo "Please answer yes or no.";;
	esac
done

# umount all and wipe 8K of all partitions and block device.
# Start with the partitions first because on newer
# Ubuntu systems the partition table will be reloaded when writing to a block device
# The partitions are wiped because the kernel otherwise tries to remount them after the
# new partition table is written to the device
for n in $(reverse ${DEV}*)
do
	echo "Unmounting ${n}"
	umount $n || true
	dd if=/dev/zero of=$n bs=8192 count=1
done

set -e

# Partition the disk, as 64M FAT16, 1500MB root and the rest data
parted --align optimal --script ${DEV} -- \
	mklabel msdos \
	mkpart primary fat16 1MiB 64MiB \
	mkpart primary ext4 64MiB 1564MiB \
	mkpart primary ext4 1564MiB -1s

# Wait until kernel reloaded partition table
echo -n "Waiting for partition table to reload "
while [ ! -e "${DEV}1" ] || [ ! -e "${DEV}2" ] || [ ! -e "${DEV}3" ]
do
	echo -n "."
	sleep 0.1
done

echo ""

# format the DOS part
mkfs.vfat -n "boot" -F 16 ${DEV}1

# Format the Linux rootfs part
mkfs.ext4 -m 0 -L "rootfs" -b 2048 -N 200000 -O sparse_super,dir_index  ${DEV}2

# Format the Linux data part, optimize for large files
mkfs.ext4 -m 0 -L "data" -O large_file,sparse_super,dir_index,bigalloc -C 262144 ${DEV}3

echo "---"
echo "Operation completed"
echo "Please eject your SD card and re-insert it"
