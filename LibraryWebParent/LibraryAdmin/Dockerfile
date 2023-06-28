FROM openjdk:17

ARG JAR_FILE=./target/LibraryAdmin-1.0.1.jar

COPY ${JAR_FILE} adminApp.jar

ENTRYPOINT ["java", "-jar", "/adminApp.jar"]