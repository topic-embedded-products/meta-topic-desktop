do_install_append() {
  sed -i 's/NoDisplay=true/NoDisplay=false/' ${D}${datadir}/applications/evince.desktop
}
