FROM openjdk:22-jdk-slim

WORKDIR /app

COPY target/emprestimo-service-0.0.1-SNAPSHOT.jar emprestimo-service.jar

EXPOSE 8082

ENV SERVER_PORT=8082

CMD ["java", "-jar", "emprestimo-service.jar"]
