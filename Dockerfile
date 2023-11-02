FROM openjdk:8-jdk-alpine
MAINTAINER hungdz
COPY target/miukit.jar miukit.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/miukit.jar"]