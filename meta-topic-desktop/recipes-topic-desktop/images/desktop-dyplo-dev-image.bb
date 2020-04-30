DESCRIPTION = "Desktop image including development tools and source code for Dyplo demonstrator(s)"

# Extending my-image
require desktop-image.bb

MY_DEVELOPMENT_EXTRAS += "\
	dyplo-qt-image-demo-source \
	git \
	libdyplo-dev \
	packagegroup-core-sdk \
	qt5-creator \
	"
