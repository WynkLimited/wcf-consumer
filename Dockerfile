From maven:3.9.0-amazoncorretto-19
COPY . .
RUN export PROFILE=stage \
    && mvn clean install 
