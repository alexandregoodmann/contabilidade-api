clear
sudo mvn clean package -DskipTests=true
sleep 5

docker-compose up
