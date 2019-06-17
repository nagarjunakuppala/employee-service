FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/truck-tracker-service-0.0.1-SNAPSHOT.jar api.jar
ENTRYPOINT ["java","-Djava.security.edg=file:/dev/./urandom","-jar","/api.jar"] 
