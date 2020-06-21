LICENSE = "Apache-2.0"
HOMEPAGE = "https://gitlab.iqrf.org/open-source/iqrf-gateway-daemon"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

inherit cmake systemd python3native

DEPENDS = "shape shapeware python3-native python3-requests-native"
RDEPEND_${PN} += "shape shapeware"

SRCREV = "bab8cf108d7b7edf05d728e11993ee416f8a8f3b"

SRC_URI = "gitsm://gitlab.iqrf.org/open-source/iqrf-gateway-daemon.git;protocol=https;branch=master \
	file://0001-First-working-verion.patch \
	file://0002-wip.patch \
	file://iqrf-gateway-daemon.service \
"

S = "${WORKDIR}/git"

FILES_${PN} += "/usr/runcfg/* ${sysconfdir} ${libdir}/iqrf-gateway-daemon/*"

INSANE_SKIP_${PN}-dev += "dev-elf"

SYSTEMD_SERVICE_${PN} = "iqrf-gateway-daemon.service"

IQRF_BASE_CFG_PATH = "${S}/src//start-IqrfDaemon/"

EXTRA_OECMAKE_append = " -DDAEMON_VERSION=v-master-${SRCREV}-${CI_PIPELINE_ID}"

do_install_append() {
# install systemd
	install -d ${D}${systemd_unitdir}/system
	install -m 644 ${WORKDIR}/iqrf-gateway-daemon.service ${D}${systemd_unitdir}/system/

# install extra directories
	install -d ${D}${sysconfdir}
	install -d ${D}${sysconfdir}/iqrf-gateway-daemon

	cp -r ${IQRF_BASE_CFG_PATH}/configuration/* ${D}${sysconfdir}/iqrf-gateway-daemon/
	cp -r ${IQRF_BASE_CFG_PATH}/configuration-LinDeploy/* ${D}${sysconfdir}/iqrf-gateway-daemon

	cp -r ${IQRF_BASE_CFG_PATH}/cfgSchemas ${D}${sysconfdir}/iqrf-gateway-daemon

	install -d ${D}${sysconfdir}/iqrf-gateway-daemon/certs
	install -d ${D}${sysconfdir}/iqrf-gateway-daemon/certs/core

	install -d ${D}/var/cache/iqrf-gateway-daemon/upload
	install -d ${D}/var/cache/iqrf-gateway-daemon
	
	# run update cache
	python3 ${S}/src/start-IqrfDaemon/iqrfRepoCache/update-cache.py

	cp -r ${IQRF_BASE_CFG_PATH}/iqrfRepoCache ${D}/var/cache/iqrf-gateway-daemon/
	cp -r ${IQRF_BASE_CFG_PATH}/metaData ${D}/var/cache/iqrf-gateway-daemon/
	cp -r ${IQRF_BASE_CFG_PATH}/scheduler ${D}/var/cache/iqrf-gateway-daemon/
	
	install -d ${D}/usr/share/iqrf-gateway-daemon
	install -d ${D}/usr/share/iqrf-gateway-daemon/apiSchemas
	cp -r ${S}/api/*.json ${D}/usr/share/iqrf-gateway-daemon/apiSchemas

	cp -r ${IQRF_BASE_CFG_PATH}/javaScript ${D}/usr/share/iqrf-gateway-daemon
	cp -r ${IQRF_BASE_CFG_PATH}/DB ${D}/usr/share/iqrf-gateway-daemon/

	install -d ${S}/api ${D}/usr/share/iqrf-gateway-daemon

	install -d ${D}${libdir}/iqrf-gateway-daemon

	LIBS=`find ${B}/bin -type f -name "*.so"`
	for l in $LIBS; do
		bbwarn "${l}"
		cp ${l} ${D}${libdir}/iqrf-gateway-daemon
	done
}
