# dojo18-bigdata-hotness

## Evaluation des Smack Stacks
**S**park:
**M**esos: Mesos wird repräsentiert durch DC/OS und dient der Steuerung und Provisionierung eines Clusters.
**A**kka:
**C**assandra: Distributed NoSQL-Datenbank
**K**afka: Distributed Queue

Evaluationsergebnisse zu DC/OS finden sich in [DCOS.md](DCOS.md)

## VagrantConfig
Die Original-Konfiguration für DC/OS aus dem [DC/OS Repository](https://github.com/dcos/dcos-vagrant) benötigt
ca. 12 GB Arbeitsspeicher und 8 virtuelle Prozessoren. Für Notebooks ist diese Hardware-Konfiguration zu
umfassend.

Die VagrantConfig enthält einen Master-Node, einen Private Agent, einen normalen Slave und den Cluster-Controller.

Die Datei wird in das ausgecheckte Repository von DC/OS kopiert und die Installation wie folgt durchgeführt:

```
git clone https://github.com/dcos/dcos-vagrant
cd dcos-vagrant
cp dojo18-bigdata-hotness/VagrantConfig.yaml .
vagrant up
```
## DC/OS und SMACK
Der Smack-Stack kann über die DC/OS CLI installiert werden. Die CLI ist zunächst über das Web-Frontend
zu installieren. Nach der Authentifizierung über `dcos auth login` können zusätzliche Pakete über
`dcos package install` installiert werden.

### Cassandra
Installiert wird Cassandra über `dcos install package cassandra`.

## Kafka
Entnommen aus [Kafka-Quickstart](https://kafka.apache.org/quickstart)

* Kafka herunterladen (https://www.apache.org/dyn/closer.cgi?path=/kafka/1.1.0/kafka_2.11-1.1.0.tgz)
* Kafka an gewünschten Ort extrahieren (`tar -xzf kafka_2.11-1.1.0.tgz`)
* In den extrahierten Ordner wechseln (`cd kafka_2.11-1.1.0`)

Anweisungen für Windows (relativ aus kafka-home):
```
bin/windows/zookeeper-server-start.bat config/zookeeper.properties
bin/windows/kafka-server-start.bat config/server.properties
```

## Java-Programm für CSV-Import
Derzeit ist das Programm nur in der Lage eine CSV-Datei aus dem Resources-Ordner
in Kafka hineinzupushen. Folgende Voraussetzungen müssen gelten:

* Zookeeper und Kafka laufen und nutzen Standard-Ports
* In Kafka existiert eine Topic namens _tweets_.

Programm ist ausführbar über `mvn exec:java`, wenn man sich im Working-Directory von _csv-to-kafka-twitter-producer_ befindet.
Um zu sehen, ob die Daten wirklich in Kafka landen, kann der von Kafka mitgelieferte console-consumer
in der Shell gestartet werden.
Der Consumer lässt sich mit folgender Anweisung starten (Script befindet sich im kafka-bin-Ordner, unter Unix .bat durch .sh ersetzen):
`./kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic tweets`

## Spark
Anleitung für die lokale Installation:
* Spark herunterladen
* [Win-utils](https://github.com/steveloughran/winutils/blob/master/hadoop-2.6.0/bin/winutils.exe) herunterladen
* User-Variable SPARK_HOME setzen mit Wert des Home-Directories des extrahierten Spark-Ordners
* System-Variable PATH erweitern um Wert aus SPARK_HOME\bin
* User-Variable HADOOP_HOME setzen auf gleichen Ordner wie SPARK_HOME

Generelle Quellen, woraus die Anleitung erzeugt wurde:
* http://www.ics.uci.edu/~shantas/Install_Spark_on_Windows10.pdf
* https://stackoverflow.com/a/39525952

Um zu testen, ob das Setup erfolgreich war, in einer neu-geöffneten Konsole `spark-shell`
ausführen. Es sollten keine Fehlermeldungen zu sehen sein.

Es ist möglich eine Spark-Applikation in IntelliJ auszuführen und zu debuggen. Dazu muss folgende Dependency über Maven eingebunden werden:
Das Maven-Modul `spark-kafka-to-system-out` kann in IntelliJ ausgeführt werden.
Dazu muss die Run-Configuration folgende Einstellungen besitzen:
* Working-Directory: Ordner des Maven-Moduls `spark-kafka-to-system-out`
* Environment Variables: HADOOP_HOME = Gleicher Ordner wie oben