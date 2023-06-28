FROM openjdk:17

ARG JAR_FILE=./target/LibraryFront-1.0.1.jar

COPY ${JAR_FILE} frontApp.jar

ENTRYPOINT ["java", "-jar", "/frontApp.jar"]