LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.md;md5=3e19dae9e69cafdd840726eeac0c320e"

inherit cmake

DEPENDS = "shape websocketpp curl boost zeromq asio paho-mqtt-c"

SRCREV = "3e27a7e35642d228b5f300c545a770036528b605"

SRC_URI = "git://github.com/logimic/shapeware.git;protocol=https;branch=master \
	file://0001-wip-Working-version.patch \
	file://0002-wip.patch \
"

S = "${WORKDIR}/git"

FILES_${PN} += "/usr/runcfg/* /usr/lib/*"

INSANE_SKIP_${PN}-dev += "dev-elf"

do_install_append() {
	install -d ${D}${libdir}/iqrf-gateway-daemon

	SHAPELIBS=`find ${B}/bin -type f -name "*.so"`
        for f in $SHAPELIBS; do
	        cp $f ${D}${libdir}/iqrf-gateway-daemon/
		rm -rf ${D}/{libdir}/basename $f
        done

	
	rm ${D}${bindir}/startup
	rm -rf ${D}${bindir}
}
