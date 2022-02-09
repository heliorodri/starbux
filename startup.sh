#!/bin/bash

verifyDocker() {
    if [ "$(docker ps -q -f name=$dockername)" ]; then
        if [ "$(docker ps -aq -f status=exited -f name=$dockername)" ]; then
            docker rmi -f $dockername
        else
            echo "Dockers are running, please run 'docker-compose down' before startup.sh"
            exit 1
        fi
    fi
}

verifyDocker "starbux"

echo
echo "building starbux-service"
echo

./gradlew clean build

echo
echo "Building starbux-service and database dockers"

docker-compose up -d

echo
echo " starbux-service is running on port 8080"
echo " database is running on port 5452"