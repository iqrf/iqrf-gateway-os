FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " file://avahi-daemon.conf \
	file://iqrf.service \
	file://ssh.service \
"

do_install_append() {
	install -d ${D}${sysconfdir}/avahi
	install -d ${D}${sysconfdir}/avahi/services
	
	install -m 644 ${WORKDIR}/avahi-daemon.conf ${D}${sysconfdir}/avahi
	install -m 644 ${WORKDIR}/iqrf.service ${D}${sysconfdir}/avahi/services
	install -m 644 ${WORKDIR}/ssh.service ${D}${sysconfdir}/avahi/services
}

