if [ -z "${MACHINE}" ]
then
	echo "MACHINE environment is not set. Set it before calling this"
	echo "script. Note that 'sudo' will not pass your environment"
	echo "along."
	exit 1
fi
if [ -z "${IMAGE}" ]
then
	echo "IMAGE environment is not set. Set it before calling this"
	echo "script. Note that 'sudo' will not pass your environment"
	echo "along."
	exit 1
fi
if [ -z "${IMAGE_ROOT}" ]
then
	IMAGE_ROOT=tmp-glibc/deploy/images/${MACHINE}
fi
# Ubuntu <14 uses /media for mounts, Ubuntu 14 uses /media/$USER
if [ -d /media/${SUDO_USER} ]
then
	MEDIA=/media/${SUDO_USER}
else
	MEDIA=/media
fi

if [ -d ${MEDIA}/data ]
then
	MEDIA_DATA=${MEDIA}/data
else
	MEDIA_DATA=${MEDIA}/boot
fi
MEDIA_BOOT=${MEDIA}/boot
MEDIA_ROOTFS=${MEDIA}/rootfs

# support manually supplied paths:
if [ ! -z "$1" ]
then
	MEDIA_BOOT=$1
fi
if [ ! -z "$2" ]
then
	MEDIA_ROOTFS=$2
fi
if [ ! -z "$3" ]
then
	MEDIA_DATA=$3
fi

if [ ! -w ${MEDIA_BOOT} ]
then
	echo "${MEDIA_BOOT} is not accesible. Are you root (sudo me),"
	echo "is the SD card inserted, and did you partition and"
	echo "format it with partition_sd_card.sh?"
	echo ""
	echo "This script assumes the SD card is mounted in /media.";
	echo "If this is not the case, you can supply the paths manually:";
	echo "usage: install_to_sd_desktop_image.sh [pathToBootFileSystem] [pathToRootFileSystem] [pathToDataFileSystem]"
	exit 1
fi
if [ ! -w ${MEDIA_ROOTFS} ]
then
	echo "${MEDIA_ROOTFS} is not acccesible. Are you root (sudo me),"
	echo "is the SD card inserted, and did you partition and"
	echo "format it with partition_sd_card.sh?"
	echo ""
	echo "This script assumes the SD card is mounted in /media.";
	echo "If this is not the case, you can supply the paths manually:";
	echo "usage: install_to_sd_desktop_image.sh [pathToBootFileSystem] [pathToRootFileSystem] [pathToDataFileSystem]"
	exit 1
fi
if [ ! -w ${MEDIA_DATA} ]
then
	echo "${MEDIA_DATA} is not acccesible. Are you root (sudo me),"
	echo "is the SD card inserted, and did you partition and"
	echo "format it with partition_sd_card.sh?"
	echo ""
	echo "This script assumes the SD card is mounted in /media.";
	echo "If this is not the case, you can supply the paths manually:";
	echo "usage: install_to_sd_desktop_image.sh [pathToBootFileSystem] [pathToRootFileSystem] [pathToDataFileSystem]"
	exit 1
fi
if [ ! -f ${IMAGE_ROOT}/${IMAGE}-${MACHINE}.tar.gz ]
then
	echo "Image '${IMAGE}' does not exist, cannot flash it."
	echo "Make sure your are calling this script from the \"build\" directory."
	echo ${IMAGE_ROOT}/${IMAGE}-${MACHINE}.tar.gz
	exit 1
fi
DO_UNMOUNT=1
while [ ! -z "$1" ]
do
	if [ "$1" == "-n" ]
	then
		DO_UNMOUNT=0
	fi
	shift
done
if [ -z "${DTB}" ]
then
	DTB="uImage-*-adv7511.dtb"
fi
set -e
if [ -z "${SD_BOOTSCRIPT}" ]
then
	SD_BOOTSCRIPT=autorun.scr
fi

echo "Writing boot..."
rm -f ${MEDIA_BOOT}/*.ubi ${MEDIA_BOOT}/*.squashfs-lzo
if [ -e ${IMAGE_ROOT}/BOOT.bin ]
then
	cp ${IMAGE_ROOT}/BOOT.bin ${MEDIA_BOOT}/BOOT.BIN
	if [ -e ${IMAGE_ROOT}/u-boot.img ]
	then
		cp ${IMAGE_ROOT}/u-boot.img ${MEDIA_BOOT}/
	fi
	cp ${IMAGE_ROOT}/${SD_BOOTSCRIPT} ${MEDIA_BOOT}/autorun.scr
	cp ${IMAGE_ROOT}/uImage ${MEDIA_BOOT}/
	cp ${IMAGE_ROOT}/${DTB} ${MEDIA_BOOT}/devicetree.dtb
else
	tar xaf ${IMAGE_ROOT}/boot.tar.gz --no-same-owner -C ${MEDIA_BOOT}
fi
for FS in ubi squashfs-lzo squashfs-xz
do
	if [ -f ${IMAGE_ROOT}/${IMAGE}*-${MACHINE}.${FS} ]
	then
		cp ${IMAGE_ROOT}/${IMAGE}*-${MACHINE}.${FS} ${MEDIA_DATA}
	fi
done
echo "Writing rootfs..."
if [ ! -f dropbear_rsa_host_key -a -f ${MEDIA_ROOTFS}/etc/dropbear/dropbear_rsa_host_key ]
then
	cp ${MEDIA}/rootfs/etc/dropbear/dropbear_rsa_host_key .
	chmod 666 dropbear_rsa_host_key
fi
rm -rf ${MEDIA_ROOTFS}/*
tar xzf ${IMAGE_ROOT}/${IMAGE}-${MACHINE}.tar.gz -C ${MEDIA_ROOTFS}
if [ -f dropbear_rsa_host_key ]
then
	install -d ${MEDIA_ROOTFS}/etc/dropbear
	install -m 600 dropbear_rsa_host_key ${MEDIA_ROOTFS}/etc/dropbear/dropbear_rsa_host_key
fi
if [ ! -z "${FPGA_BOOT_IMAGE}" ]
then
	cp ${MEDIA_ROOTFS}/${FPGA_BOOT_IMAGE} ${MEDIA_BOOT}/fpga.bin
fi

if [ $DO_UNMOUNT -ne 0 ]
then
	sleep 1
	echo -n "Unmounting"
	for p in ${MEDIA_BOOT} ${MEDIA_ROOTFS} ${MEDIA_DATA}
	do
		if [ -d $p ]
		then
			echo -n " $p..."
			umount $p
			if [ -d $p ]
			then
				rmdir $p
			fi
		fi
	done
	echo ""
fi
echo "done."
