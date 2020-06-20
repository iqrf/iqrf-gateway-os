#!/bin/sh

docker run -it -e USER_ID=$(id -u) --rm -v"`pwd`:/home/builder" -w "/home/builder" -v"/opt:/opt" kasproject/kas:latest  sh -c "kas build iqrf-gateway-os-config.yml"
