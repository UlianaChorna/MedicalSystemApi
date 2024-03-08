FROM openjdk:21
VOLUME /tmp
COPY target/MedicalSystemApi-0.0.1-SNAPSHOT.jar /MedicalSystemApi-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/MedicalSystemApi-0.0.1-SNAPSHOT.jar"]