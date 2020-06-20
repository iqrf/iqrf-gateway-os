#!/bin/sh

if [ ! -z "$1" ]; then
	kas shell iqrf-gateway-os-config.yml -c "$@" 
else
	kas build iqrf-gateway-os-config.yml
fi
