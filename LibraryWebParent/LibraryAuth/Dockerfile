FROM openjdk:17

ARG JAR_FILE=./target/LibraryAuth-1.0.1.jar

COPY ${JAR_FILE} authApp.jar

ENTRYPOINT ["java", "-jar", "/authApp.jar"]