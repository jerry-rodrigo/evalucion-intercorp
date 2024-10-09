FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/notification-service-0.0.1-SNAPSHOT.jar /app/notification-service.jar

ENV SPRING_PROFILES_ACTIVE=prod \
    EUREKA_URI=http://discovery-server:8761/eureka \
    KAFKA_SERVERS=kafka:9092


EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/notification-service.jar"]
