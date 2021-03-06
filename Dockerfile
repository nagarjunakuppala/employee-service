FROM openjdk:8-jdk-alpine
VOLUME /tmp

COPY ./target/truck-tracker-service-0.0.1-SNAPSHOT.jar /api.jar
COPY ./docker-entrypoint.sh /docker-entrypoint.sh

RUN chmod 755 /docker-entrypoint.sh

RUN touch /api.jar && mkdir -p /config

WORKDIR /

EXPOSE 9002

ENTRYPOINT [ "/docker-entrypoint.sh" ]

CMD [ "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /api.jar" ]
