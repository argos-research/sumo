
##############################
#
# This is a bootstrap script which is
# run at every startup of the vagrant machine
# If you want to run something just once at provisioning
# and first bootup of the vagrant machine please see
# provision.sh
#
# Contributor: Bernhard Blieninger
##############################

cd /vagrant

# fix automake
aclocal
automake --add-missing
autoreconf --force --install

./configure CXXFLAGS="--std=c++11"
make -j2
