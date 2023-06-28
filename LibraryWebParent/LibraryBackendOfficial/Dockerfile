FROM openjdk:17

ARG JAR_FILE=./target/LibraryBackendOfficial-1.0.1.jar

COPY ${JAR_FILE} officialsApp.jar

ENTRYPOINT ["java", "-jar", "/officialsApp.jar"]