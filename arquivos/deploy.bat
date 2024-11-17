@echo off
cls
cd contabilidade-api
call mvn clean package -DskipTests=true
cd ..
cd contabilidade-web2
call ng build
cd ..
call docker-compose stop
call docker-compose up --build &