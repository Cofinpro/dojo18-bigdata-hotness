# Kafka

## Technische Details
In DC/OS kann Kafka einfach als Service installiert und skaliert werden. Durch die Skalierung ergibt sich die Anzahl der verfügbaren Broker. D.h. wird kafka auf 5 Instanzen skaliert, so stehen 5 Broker zur Verfügung. Neben der Administration über das Web UI lässt sich Kafka auch über die DC/OS CLI administrieren.

Auf dieser Ebene lassen sich auch weitere Informationen über Kafka entlocken. So lässt sich die _Kafka CLI_ mittels `dcos package install kafka --cli` installieren. Nach der Installation stehen über `dcos kafka` neue Befehle zur Administration von Kafka zur Verfügung.

## Das Szenario
In DC/OS soll Kafka genutzt werden, um die aus dem [CSV Twitter Producer](csv-to-kafka-twitter-producer) erzeugten Tweets entgegen zu nehmen und vorzuhalten. Im weiteren Use-Case sollen die Tweets durch Spark analysiert werden.

### Aufsetzen des Topics
Jedes Publisher-Subscriber-Pattern benötigt ein **Topic** über das sich die _Teilnehmer_ unterhalten können. In DC/OS gescheit dies über das folgende Kommando:

```
dcos kafka topic create tweets --partitions 1 --replication 1
```

Damit steht dem Twitter Producer das Topic tweets zur Verfügung.

### Deployment des Twitter Producers
Der Twitter Producer liest aus einer CSV Datei offline Tweets aus und sendet diese an Kafka. Hierzu wurde der erste Wurf der Java-Anwendung parametrierbar gemacht, sodass die Adresse der virtuellen IP-Adresse der Kafka-Instanzen beim Ausrollen der Anwendung mitgegeben werden kann.

Die Java-Anwendung wird anschließend in einen Docker-Container gehüllt und über die DC/OS CLI installiert. Details dazu finden sich im Verzeichnis [csv-to-kafka-twitter-producer](../csv-to-kafka-twitter-producer).

### Beobachten der produzierten Daten
Kafka zugrunde liegt Apache Zookeper für das Management der verschiedenen Broker-Instanzen. Für die Verbindung mit dem Consumer Client auf die Message Queue wird der Hostname benötigt, unter dem Zookeper für Kafka zu erreichen ist. Da die Dokumentation zu DC/OS an dieser Stelle etwas veraltet ist, muss der Host selbständig über `dcos kafka endpoint "zookeeper"` ermittelt werden.

Das Ergebnis könnte beispielsweise wie folgt aussehen:

```
master.mesos:2181/dcos-service-kafka
```

Um die produzierten Daten in der Message-Queue zu sehen, kann man sich mit dem Kafka Console Consumer verbinden. Dazu sollte man zunächst mit dem Master-Knoten von DC/OS verbunden sein:

```
dcos node ssh --master-proxy --leader
```

Und anschließend einen Kafka-Client über `docker run -it mesosphere/kafka-client` starten. Natürlich ist auch dieser in einen Docker-Container gepackt. Anschließend haben wir eine Shell zur Verfügung, über die wir uns mit dem _Kafka Console Consumer_ die eingehenden Daten ansehen können:

```
root@7d0aed75e582:/bin# ./kafka-console-consumer.sh --zookeeper master.mesos:2181/dcos-service-kafka --topic tweets --from-beginning
```
 
