# The examples fail to build and we don't really need them,
# so tell QT to skip building them.
QT_CONFIG_FLAGS += "-nomake examples"

# Webkit fails to build on 32-bit, so skip that too.
QT_WEBKIT = "-no-webkit"

# Pending submission in mainline, should have been in qt4-${PV}.inc
SRC_URI += "file://0038-3rdparty-javascriptcore-JITStubs.cpp-allow-builds-of.patch"
