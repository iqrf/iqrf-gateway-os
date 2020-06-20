FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " file://wg0.conf"

FILES_${PN} += "${sysconfdir}/*"

do_install_append() {
	install -d ${D}${sysconfdir}/wireguard
	install -m 644 ${WORKDIR}/wg0.conf ${D}${sysconfdir}/wireguard

	install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants
	ln -sf ${systemd_unitdir}/system/wg-quick@.service \
			${D}${sysconfdir}/systemd/system/multi-user.target.wants/wg-quick@wg0.service

}
