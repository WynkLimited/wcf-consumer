FROM openjdk:14-jdk-alpine
ENV SPRING_PROFILE=stage
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${SPRING_PROFILE}","/app.jar"]
