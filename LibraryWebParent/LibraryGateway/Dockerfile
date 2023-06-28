FROM openjdk:17

ARG JAR_FILE=./target/LibraryGateway-1.0.1.jar

COPY ${JAR_FILE} gatewayApp.jar

ENTRYPOINT ["java", "-jar", "/gatewayApp.jar"]