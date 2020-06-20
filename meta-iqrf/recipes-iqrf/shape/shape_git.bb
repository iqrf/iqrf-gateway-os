LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

inherit cmake

DEPENDS = "python3-native"

SRCREV = "f473ee1294f175b7bbe3cddd7833d62bf7cb60b5"

SRC_URI = "git://github.com/logimic/shape.git;protocol=https;branch=master \
	file://0001-wip-First-working-version.patch \
	file://0002-wip1-stilll-working.patch \
	file://0003-Update-rapidjson-to-8f4c021fa2f1e001d2376095928fc053.patch \
"

S = "${WORKDIR}/git"

FILES_${PN} += "/usr/runcfg/* /usr/lib/*"
FILES_${PN}-dev = "/usr/include/shape/*" 

# hack to copy library to proper location
do_install_append() {
	install -d ${D}${libdir}/iqrf-gateway-daemon
	install -m 755 ${B}/bin/libExample1_Thread.so ${D}${libdir}/iqrf-gateway-daemon
	install -m 755 ${B}/bin/libTraceFileService.so ${D}${libdir}/iqrf-gateway-daemon
	install -m 755 ${B}/bin/libTraceFormatService.so ${D}${libdir}/iqrf-gateway-daemon

	rm ${D}${bindir}/startup
	rm -rf ${D}${bindir}
}

INSANE_SKIP_${PN}-dev += "dev-elf"

