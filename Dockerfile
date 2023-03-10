From maven:3.9.0
COPY . .
RUN export PROFILE=stage \
    && mvn clean install 
