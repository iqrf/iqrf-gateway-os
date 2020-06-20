IMAGE_FEATURES = " ssh-server-dropbear debug-tweaks"

IMAGE_INSTALL = "packagegroup-core-boot \
		 packagegroup-base \
		${CORE_IMAGE_EXTRA_INSTALL} \
		kernel-modules \
		wpa-supplicant \
		monit \
		wireguard-tools \
		wireguard-module \
		iqrf-gateway-daemon \
		shape shapeware \
		iqrf-gateway-webapp \
		networkmanager \
		nano \
		mosquitto \
		mosquitto-clients \
		avahi-daemon \
		rsyslog \
		networkmanager \
		modemmanager \
		usb-modeswitch \
		coreutils \
"

IMAGE_LINGUAS = " "

inherit core-image
