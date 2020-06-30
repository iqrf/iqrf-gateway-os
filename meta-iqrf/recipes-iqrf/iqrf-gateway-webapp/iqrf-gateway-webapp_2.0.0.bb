LICENSE = "Apache-2.0"
HOMEPAGE = "https://gitlab.iqrf.org/open-source/iqrf-gateway-daemon"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

DEPENDS += "composer-native php-native openssl-native"
RDEPENDS_${PN} += "nginx php php-cgi php-cli php-fpm php-opcache php-pear php-phar"

SRC_URI = "git://gitlab.iqrf.org/open-source/iqrf-gateway-webapp.git;protocol=https;tag=v${PV} \
	file://0001-app-config-Disable-sudo.patch \
	file://0002-Makefile-fixes.patch \
"

S = "${WORKDIR}/git"

do_install() {
	export DESTDIR="${D}"
	oe_runmake install

	install -d ${D}${sbindir}
	ln -sf /usr/share/iqrf-gateway-webapp/bin/manager ${D}${sbindir}/iqrf-gateway-webapp-manager

	WEBAPP_CERT_DIR="${D}${sysconfdir}/iqrf-gateway-webapp/certs"

	# generate certificate
	if [ ! -f "${WEBAPP_CERT_DIR}/cert.pem" ] || [ ! -f "${WEBAPP_CERT_DIR}/privkey.pem" ] ; then
		openssl ecparam -name secp384r1 -genkey -param_enc named_curve -out ${WEBAPP_CERT_DIR}/privkey.pem
		openssl req -new -x509 -sha256 -nodes -days 3650 \
			-subj "/CN=IQRF Gateway/C=CZ/ST=Hradec Kralove Region/L=Jicin/O=IQRF Tech s.r.o." \
			-key ${WEBAPP_CERT_DIR}/privkey.pem -out ${WEBAPP_CERT_DIR}/cert.pem
		chmod 600 ${WEBAPP_CERT_DIR}/*.pem
	fi

	if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
		install -d ${D}${sysconfdir}/tmpfiles.d
		echo "d ${localstatedir}/log/iqrf-gateway-webapp - - - -" \
			> ${D}${sysconfdir}/tmpfiles.d/iqrf-gateway-webapp.conf
	fi
}

FILES_${PN} += "${sbindir}/* ${sysconfdir}/tmpfiles.d/*"

pkg_postinst_ontarget_${PN} () {
	/usr/sbin/iqrf-gateway-webapp-manager database:create
	/usr/sbin/iqrf-gateway-webapp-manager migrations:migrate --no-interaction
	/usr/sbin/iqrf-gateway-webapp-manager nette:latte:warmup
}

