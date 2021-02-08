#!/usr/bin/env bash

#
# Build for ARMv8 in a docker container
#

# bailout on errors and echo commands.
set -xe

DOCKER_SOCK="unix:///var/run/docker.sock"

echo "DOCKER_OPTS=\"-H tcp://127.0.0.1:2375 -H $DOCKER_SOCK -s overlay2\"" | sudo tee /etc/default/docker > /dev/null
sudo service docker restart
sleep 5;

if [ "$EMU" = "on" ]; then
  if [ "$CONTAINER_DISTRO" = "raspbian" ]; then
      docker run --rm --privileged multiarch/qemu-user-static:register --reset
  else
      docker run --rm --privileged --cap-add=ALL --security-opt="seccomp=unconfined" multiarch/qemu-user-static --reset --credential yes --persistent yes
  fi
fi

WORK_DIR=$(pwd):/ci-source

docker run --privileged --cap-add=ALL --security-opt="seccomp=unconfined" -d -ti -e "container=docker"  -v $WORK_DIR:rw $DOCKER_IMAGE /bin/bash
DOCKER_CONTAINER_ID=$(docker ps --last 4 | grep $CONTAINER_DISTRO | awk '{print $1}')

docker exec --privileged -ti $DOCKER_CONTAINER_ID apt-get update
docker exec --privileged -ti $DOCKER_CONTAINER_ID apt-get install -y autoconf libpcre3 libpcre3-dev bison flex curl tar openjdk-11-jdk git build-essential
docker exec --privileged -ti $DOCKER_CONTAINER_ID curl -L -O https://github.com/swig/swig/archive/v4.0.2.tar.gz
docker exec --privileged -ti $DOCKER_CONTAINER_ID tar -xzvf v4.0.2.tar.gz
docker cp blst $DOCKER_CONTAINER_ID:/
docker exec --privileged -ti $DOCKER_CONTAINER_ID /bin/bash -xec \
  "cd swig-4.0.2/; sh autogen.sh; ./configure --disable-dependency-tracking; make; make install; cd ../blst; export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-arm64; export JAVA_PACKAGE=tech.pegasys.teku.bls.impl.blst.swig; sh bindings/java/run.me || true;"
docker cp $DOCKER_CONTAINER_ID:/blst/tech/pegasys/teku/bls/impl/blst/swig/libblst.so src/main/resources/aarch64/

#find dist -name \*.\*$EXT

echo "Stopping"
docker ps -a
docker stop $DOCKER_CONTAINER_ID
docker rm -v $DOCKER_CONTAINER_ID