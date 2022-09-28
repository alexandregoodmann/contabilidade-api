FROM adoptopenjdk/openjdk11:ubi
VOLUME /tmp
EXPOSE 8081
COPY target/contabilidade-api-0.0.1-SNAPSHOT.jar contabilidade-api.jar
ENTRYPOINT ["java","-jar","/contabilidade-api.jar"]
