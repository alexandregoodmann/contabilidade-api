clear
sudo mvn clean package -DskipTests=true
sleep 5

docker-compose up &
sleep 5

sudo java -jar target/contabilidade-api-0.0.1-SNAPSHOT.jar
