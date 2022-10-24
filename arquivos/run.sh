clear
cd contabilidade-api
sudo mvn clean install -DskipTests=true
cd ..
cd contabilidade-web2
sudo ng build
sudo docker-compose up --build
