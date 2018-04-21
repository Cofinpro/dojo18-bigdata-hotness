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