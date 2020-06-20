#!/bin/sh

export MENDER_ARTIFACT_NAME=${CI_PIPELINE_ID}

kas build iqrf-gateway-os-config.yml:iqrf-gateway-os-config-server.yml
