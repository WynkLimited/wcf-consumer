FROM openjdk:14-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV PROFILE=staging
ENTRYPOINT ["java","-jar","/app.jar"]
