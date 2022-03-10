#!/bin/bash

# Wait until database starts
until exec 6<>/dev/tcp/${DATABASE_HOST:-postgres}/${DATABASE_PORT:-5432}; do
  echo "Database is unavailable - sleeping"
  sleep 5
done
echo "Database is up - starting service"

# Wait until simulator starts
until exec 6<>/dev/tcp/${RNIS_SIMULATOR_HOST:-simulator}/${RNIS_SIMULATOR_PORT:-8081}; do
  echo "Simulator is unavailable - sleeping"
  sleep 5
done
echo "Simulator is up - starting service"

# Run applicataion
java -jar rnis.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE:-default}
