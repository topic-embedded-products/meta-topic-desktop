SUMMARY = "Xilinx XDP IIO sensor 2"

require xdpsensors.inc

DEPENDS += "nodejs-native"

RDEPENDS_${PN} += "xdpsensors xdp-dyplo-app"

do_compile() {
    npm config set prefix '${WORKDIR}/.npm'
    npm config set HOME '${WORKDIR}'
    npm config set cache '${WORKDIR}/npm_cache'
    npm install @angular/cli@6.1.1 --prefix ${WORKDIR}/.npm
    npm --save-dev install @angular-devkit/build-angular@0.8.9 --prefix ${WORKDIR}/.npm
    cd "${WORKDIR}/git/frontend"
    npm link
    ./../../.npm/node_modules/@angular/cli/bin/ng build --prod --verbose
}

do_install_append() {
    install -d ${D}/var/www/localhost/html
    cp -R --no-dereference --preserve=mode,links -v ${S}/frontend/dist/drone-frontend/ ${D}/var/www/localhost/html
    install -m 644 ${S}/test/vid_test.mp4 ${D}/var/www/localhost/html/vid_test.mp4
}
