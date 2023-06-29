FROM openjdk:17

ARG JAR_FILE=./target/LibraryBackendReader-1.0.1.jar

COPY ${JAR_FILE} readersApp.jar

ENTRYPOINT ["java", "-jar", "/readersApp.jar"]