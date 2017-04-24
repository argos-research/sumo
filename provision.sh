
#######################
#
# This is a provision script
# it will be called once when the vagrant vm is first provisioned
# If you have commands that you want to run always please have a
# look at the bootstrap.sh script
#
# Contributor: Bernhard Blieninger
######################

sudo apt-get update -q=2
sudo apt-get install -q=2 make g++ libxerces-c-dev libfox-1.6-dev automake libtool
