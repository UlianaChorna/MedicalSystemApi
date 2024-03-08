FROM openjdk:21-jdk-slim as builder
WORKDIR /app
COPY target/MedicalSystemApi-0.0.1-SNAPSHOT.jar MedicalSystemApi-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java","-jar","MedicalSystemApi-0.0.1-SNAPSHOT.jar"]