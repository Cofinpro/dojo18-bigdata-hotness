# CSV-To-Kafka-Twitter-Producer

Dieses Modul stellt eine konventionelle Java-Anwendung dar, die
blockierend eine CSV-Datei ausliest.

Derzeit ist das Programm nur in der Lage eine CSV-Datei aus dem Resources-Ordner
in Kafka hineinzupushen. Folgende Voraussetzungen müssen gelten:

* Zookeeper und Kafka laufen und nutzen Standard-Ports
* In Kafka existiert eine Topic namens _tweets_.

Unter Umständen muss das _model_-Modul per `mvn install` gebaut werden.

Programm ist ausführbar über `mvn exec:java`, wenn man sich im Working-Directory von _csv-to-kafka-twitter-producer_ befindet.
Um zu sehen, ob die Daten wirklich in Kafka landen, kann der von Kafka mitgelieferte console-consumer
in der Shell gestartet werden.
Der Consumer lässt sich mit folgender Anweisung starten (Script befindet sich im kafka-bin-Ordner, unter Unix .bat durch .sh ersetzen):
`./kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic tweets`

## Commandline-Options
```
usage: <this application>
 -b,--bootstrap-servers <arg>   addresses of kafka bootstrap servers
                                (multiple values allowed, separated by ','
 -h,--help                      shows this help text
 ```

## Integration mit Docker
Der Producer kann in einen Docker-Container gehüllt werden, um diesen auf DC/OS bzw. Mesos auszurollen:

```
docker build -t cofinpro/twitter-producer .
docker push cofinpro/twitter-producer
```

Mit dem letzten Befehl wird das Image auf [Docker Hub](https://hub.docker.com) zur Verfügung gestellt.

Über diesen Weg kann dann die Anwendung über das Script [twitter-producer.json](../marathon/scripts/twitter-producer.json) auf DC/OS bereitgestellt werden. Details zur Bereitstellung mit Marathon auf DC/OS finden sich im Verzeichnis [marathon](../marathon).

### Troubleshooting
Beim Bereitstellen der Images auf Docker Hub sollte darauf geachtet werden, dass die Images mit einer Versionsnummer getaggt werden und nicht ausschließlich auf das Tag __latest__ bereitgestellt werden.

Wird die Java-Anwendung verändert und in einem modifizierten Image bereitgestellt, so findet das Script zur Installation in Mesos bzw. DC/OS keine Veränderung und nutzt wieder den alten Container, der bereits im lokalen Repository von DC/OS liegt.

# TODO
Die Integration in den Docker-Container ist noch nicht perfekt. Der Producer läuft sehr lange im Docker-Container und enthält aktuell noch keine Kontrollfunktion für die [HealthChecks](http://mesosphere.github.io/marathon/docs/health-checks.html), sodass aktuell der Producer trotz laufender Verarbeitung durch Marathon "gekillt" und neu gestartet wird. Als nächster Schritt muss entweder die Java-Anwendung einen HaealthCheck zur Verfügung stellen oder der HealthCheck so konfiguriert werden, auf das Log zu lauschen.

Weitere Informationen und Möglichkeiten zu HealthChecks finden sich auch in der aktuellen [Dokumentation von Mesosphere](https://docs.mesosphere.com/1.11/deploying-services/creating-services/health-checks/).
