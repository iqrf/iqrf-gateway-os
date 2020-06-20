LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://composer.phar;md5=ddeb36c4e7970a08c184038f53b1832d"

SRC_URI = "https://getcomposer.org/download/${PV}/composer.phar"

SRC_URI[md5sum] = "ddeb36c4e7970a08c184038f53b1832d"
SRC_URI[sha256sum] = "29bdac1bda34d8902b9f9e4f5816de08879b8f3fafad901e4283519cdefbee7b"

S="${WORKDIR}"

do_install() {
	install -d ${D}${bindir}
	install -m 755 ${S}/composer.phar ${D}${bindir}/composer
}

BBCLASSEXTEND = "native"
