FROM openjdk:18
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} greaseandsolid-service.jar
ENTRYPOINT ["java","-jar","/greaseandsolid-service.jar"]