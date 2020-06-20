FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = "file://NetworkManager.conf"

FILES_${PN} += "${sysconfdir}/*"

do_install_append() {
	install -d ${D}${sysconfdir}/NetworkManager
	install -m 644 ${WORKDIR}/NetworkManager.conf ${D}${sysconfdir}/NetworkManager
}
