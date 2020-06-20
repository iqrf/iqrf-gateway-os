FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

DEPENDS += "zlib libzip"

DEPENDS_class-native += "libzip-native"

EXTRA_OECONF_append = " --enable-fpm --enable-mbstring --enable-zip"

EXTRA_OECONF_class-native = " \
	--with-zlib=${STAGING_LIBDIR_NATIVE}/.. \
	--with-libzip=${STAGING_LIBDIR_NATIVE}/.. \
	${COMMON_EXTRA_OECONF} \
"
PACKAGECONFIG_class-native= "mysql sqlite3 opcache openssl \
	${@bb.utils.filter('DISTRO_FEATURES', 'ipv6 pam', d)} \
"

SRC_URI_append = " file://iqrf-gateway-webapp.conf"


do_install_append() {
	install -d ${D}${sysconfdir}/php-fpm.d/
	install -m 755 ${WORKDIR}/iqrf-gateway-webapp.conf ${D}${sysconfdir}/php-fpm.d/
}

FILES_${PN}-fpm += "${sysconfdir}/php-fpm.d/*"
