SUMMARY = "MySQL Connector/Python enables Python programs to access MySQL databases."
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=e94afccb06cf8ffefdd2361ea575a821"

SRC_URI = "git://github.com/mysql/mysql-connector-python.git;protocol=https;branch=master"

RDEPENDS:${PN} = "python3"

inherit setuptools3

S = "${WORKDIR}/git"
PV = "8.0.28"
SRCREV = "90eaeca65a6bbfc1fd9218aad5303957798215c3"

