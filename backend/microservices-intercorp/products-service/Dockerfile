FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/products-service-0.0.1-SNAPSHOT.jar /app/products-service.jar

ENV SPRING_PROFILES_ACTIVE=prod \
    EUREKA_URI=http://discovery-server:8761/eureka \
    KAFKA_SERVERS=kafka:9092

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/products-service.jar"]
