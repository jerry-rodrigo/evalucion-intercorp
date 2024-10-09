FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/discovery-server-0.0.1-SNAPSHOT.jar /app/discovery-server.jar

ENV SPRING_PROFILES_ACTIVE=prod \
    #EUREKA_URI=http://eureka:password@localhost:8761/eureka
    EUREKA_URI=http://discovery-server:8761/eureka

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/app/discovery-server.jar"]