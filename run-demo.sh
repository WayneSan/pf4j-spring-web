#!/usr/bin/env bash

#Build
mvn -pl com.example:pf4j-spring-web-parent,pf4j-spring-web-api clean install -DskipTests || exit $?
mvn -f ./pf4j-spring-web-plugins/pom.xml clean package -DskipTests || exit $?

# create the plugins folder
rm -rf ./plugins-dist/
mkdir ./plugins-dist/

# copy artifacts to plugins folder
cp ./pf4j-spring-web-plugins/*/target/*.zip ./plugins-dist/

mvn -pl pf4j-spring-web clean spring-boot:run
