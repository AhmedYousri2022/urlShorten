FROM eclipse-temurin:17-jre AS builder
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} urlshortening-1.0.0.jar

ENTRYPOINT ["java","-jar","/urlshortening-1.0.0.jar"]
