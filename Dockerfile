FROM openjdk:17-jdk-slim
COPY target/belejki.rest-0.0.1-SNAPSHOT.jar belejki.rest.jar
ENTRYPOINT ["java", "-jar", "/belejki.rest.jar"]
