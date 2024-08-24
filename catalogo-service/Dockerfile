FROM openjdk:22-jdk-alpine

WORKDIR /app

COPY target/catalogo-service-0.0.1-SNAPSHOT.jar catalogo-service.jar

EXPOSE 8083

CMD ["java", "-jar", "catalogo-service.jar"]
