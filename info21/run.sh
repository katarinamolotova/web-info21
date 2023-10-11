#!/bin/bash

#echo "Prepare directoty struct"
#if [ -d $(pwd)/../dist ]; then
#    rm -rf $(pwd)/../dist;
#    echo "Catalog structure rebuilding"
#fi
#
#mkdir $(pwd)/../dist;
#echo "Created dist directory"
#mkdir $(pwd)/../dist/database;
#echo "Created database directory"
#mkdir $(pwd)/../dist/export;
#echo "Created export directory"
#mkdir $(pwd)/../dist/docker-entrypoint-initdb;
#echo "Created docker-entrypoint-initdb directory"

#cp $(pwd)/part1/* $(pwd)/../dist/docker-entrypoint-initdb/
#cp $(pwd)/helper/docker-compose.yml $(pwd)/../dist
#cd ../dist

docker-compose down;
rm -rf dist
#docker-compose up -d --no-deps --build service-backend

docker-compose up --build
#docker-compose up;
#docker-compose up -d;
