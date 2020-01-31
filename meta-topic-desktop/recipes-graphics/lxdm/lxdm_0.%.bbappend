# Automatically logon as "user"
do_compile_append() {
    sed -i -e 's,# autologin=.*,autologin=user,g' ${S}/data/lxdm.conf.in
    # add default configuration (again)
    oe_runmake -C ${B}/data lxdm.conf
}
