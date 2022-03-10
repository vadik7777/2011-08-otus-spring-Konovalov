copy standalone.env .env

set COMPOSE_DOCKER_CLI_BUILD=1
set DOCKER_BUILDKIT=1
docker-compose --env-file .env up --build --no-deps --remove-orphans