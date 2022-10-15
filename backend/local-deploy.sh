#!/bin/bash

DOCKER_ACCOUNT=""
THANKOO_VERSION=""

if [ "$1" == "-u" ] || [ "$1" == "-user" ] && [ -n "$2" ];
then
    DOCKER_ACCOUNT="$2"
else
  echo "input docker account! ex: -u yhh1056"
  exit 1
fi

if [ "$3" == "-v" ] || [ "$1" == "-version" ] && [ -n "$4" ];
then
    THANKOO_VERSION="$4"
else
  echo "input build version! ex: -v 1.0.0"
  exit 1
fi

function docker_build() {
  echo "=== build start ==="
  ./gradlew clean build
  docker build . -t "${DOCKER_ACCOUNT}"/thankoo:"${THANKOO_VERSION}"
  docker push "${DOCKER_ACCOUNT}"/thankoo:"${THANKOO_VERSION}"
}

docker_build


