FROM openjdk:18
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} pay-service.jar
ENTRYPOINT ["java","-jar","/pay-service.jar"]