if [ "$(id -u)" != "0" ]; then
   echo "This script must be run as root" 1>&2
   exit 1
fi

if [ -z "$1" ]
then
	echo "Usage: $0 [device]"
	echo "no device found."
	exit 1
else
	DEV=$1
fi

echo "Make sure you are using the correct device, this operation will CLEAR ALL DATA!"
while true; do
    read -p "Are you sure you wish to clear, partition and format ${DEV}? " yn
    case $yn in
        [Yy]* ) break;;
        [Nn]* ) exit;;
        * ) echo "Please answer yes or no.";;
    esac
done

# unmount all
for n in ${DEV}*
do
  echo "Unmounting ${n}"
  umount $n
done

set -e

# clear the (start of) the device to remove the MBR
dd if=/dev/zero of=${DEV} bs=8192 count=1

# Partition the disk, as 64M FAT16, 1500MB root and the rest data
sfdisk -uM ${DEV} << EOF
0 62 6
,1500
,

EOF

# format the DOS part
mkfs.vfat -n "boot" ${DEV}1

# Format the Linux rootfs part
mkfs.ext4 -m 0 -L "rootfs" -b 2048 -N 200000 -O sparse_super,dir_index  ${DEV}2

# Format the Linux data part, optimize for large files
mkfs.ext4 -m 0 -L "data" -O large_file,sparse_super,dir_index,bigalloc -C 262144 ${DEV}3

echo "---"
echo "Operation completed"
echo "Please eject your SD card and re-insert it"
