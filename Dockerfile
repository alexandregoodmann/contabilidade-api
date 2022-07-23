FROM openjdk:8-jdk-alpine
MAINTAINER alexandregoodmann.com
COPY target/contabilidade-api-0.0.1-SNAPSHOT.jar contabilidade-api.jar
ENTRYPOINT ["java","-jar","/contabilidade-api.jar"]
