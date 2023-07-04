FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} collection-service.jar
ENTRYPOINT ["java","-jar","/collection-service.jar"]