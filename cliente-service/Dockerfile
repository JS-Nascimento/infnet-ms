FROM openjdk:22-jdk-slim

WORKDIR /app

COPY target/cliente-service-0.0.1-SNAPSHOT.jar cliente-service.jar

EXPOSE 8081

ENV SERVER_PORT=8081

CMD ["java", "-jar", "cliente-service.jar"]
