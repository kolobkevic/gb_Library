FROM openjdk:17

ARG JAR_FILE=./target/LibraryReadersPersonalData-1.0.1.jar

COPY ${JAR_FILE} personalDataApp.jar

ENTRYPOINT ["java", "-jar", "/personalDataApp.jar"]