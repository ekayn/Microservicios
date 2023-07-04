FROM openjdk:18
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} supplier-service.jar
ENTRYPOINT ["java","-jar","/supplier-service.jar"]