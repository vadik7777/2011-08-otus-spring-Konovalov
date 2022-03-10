#!/usr/bin/env bash

cp -fv ./standalone.env ./.env

export COMPOSE_DOCKER_CLI_BUILD=1
export DOCKER_BUILDKIT=1
docker-compose --env-file .env up --build --no-deps --remove-orphans