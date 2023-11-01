#!/bin/bash

# =============== Prepare service structure ===============

echo "Prepare directory struct"
if [ -d $(pwd)/dist ]; then
    rm -rf $(pwd)/dist;
    rm -rf $(pwd)/target;
    rm -rf config_backend.env;
    rm -rf config_db.env;
    rm -rf config_pgadmin.env;
    rm -rf docker-compose.yml;
    rm -rf Dockerfile;
    echo "Catalog structure rebuilding"
fi

if [ $# = 0 ]; then
  mkdir $(pwd)/dist;
  mkdir $(pwd)/dist/nginx;
  mkdir $(pwd)/dist/flyway_config;
  mkdir $(pwd)/dist/flyway_migration;
  mkdir $(pwd)/dist/import;
  echo "Created dist directory structure"


  cp -r $(pwd)/helper/data_for_import/ $(pwd)/dist/import;
  cp -r $(pwd)/helper/flyway_migration/ $(pwd)/dist/flyway_migration;
  cp -r $(pwd)/helper/flyway_config/ $(pwd)/dist/flyway_config;
  cp -r $(pwd)/helper/nginx/ $(pwd)/dist/nginx;
  cp -r $(pwd)/helper/configs/ $(pwd);
  cp -r $(pwd)/helper/docker/ $(pwd);
  echo "Copy file for launch"

  docker-compose down;
  docker-compose up --build
#  docker-compose up;
fi

