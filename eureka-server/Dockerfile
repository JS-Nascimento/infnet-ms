FROM openjdk:22

WORKDIR /app

COPY target/eureka-server-0.0.1-SNAPSHOT.jar eureka-server.jar

EXPOSE 8761

CMD ["java", "-jar", "eureka-server.jar"]
