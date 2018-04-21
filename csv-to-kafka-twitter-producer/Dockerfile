FROM openjdk:8

LABEL maintainer="rollin.hand@gmx.de"
LABEL version="1.2"

ADD target/twitterProducer-jar-with-dependencies.jar /var/tmp/java/twitterProducer.jar
WORKDIR /var/tmp/java

#CMD ["/usr/bin/java", "-jar", "twitterProducer.jar", "-b$KAFKA_BROKER"]

#CMD ["/usr/bin/java", "-version"]
#CMD ["catalina.sh", "run"]