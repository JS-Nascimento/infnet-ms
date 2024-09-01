FROM openjdk:22-jdk-slim

WORKDIR /app

COPY target/catalogo-service-0.0.1-SNAPSHOT.jar catalogo-service.jar

# Expondo a porta configurada no application.yml
EXPOSE 8083

ENV SERVER_PORT=8083

# Executando a aplicação
CMD ["java", "-jar", "catalogo-service.jar"]
