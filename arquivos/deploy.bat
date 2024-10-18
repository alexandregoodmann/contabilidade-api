cd contabilidade-api
mvn clean package -DskipTests=true
cd ..
docker-compose down
docker-compose up --build