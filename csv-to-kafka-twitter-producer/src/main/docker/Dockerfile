FROM openjdk:8

LABEL maintainer="rollin.hand@gmx.de"
LABEL version="1.0"

COPY target/twitterProducer.jar /var/tmp/java
WORKDIR /var/tmp/java

CMD ["/usr/bin/java" "-jar twitterProducer.jar Application -b$KAFKA_BROKER"]

#CMD ["/usr/bin/java", "-version"]
#CMD ["catalina.sh", "run"]