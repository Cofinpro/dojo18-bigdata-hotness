# Kafka in DC/OS
Um den Kafka-Broker in DC/OS zu nutzen und zu konfigurieren, ist die Installation der DC/OS Command line tools notwendig:

`dcos package install kafka`

installiert die Kommandozeilen-Tools, sodass nun auch mit `dcos kafka` neue Funktionen auf der Kommandozeile zur Verfügung stehen.

## Topic erstellen
Über die DC/OS CLI lässt sich das Topic für die zu empfangenden Tweets erzeugen `dcos kafka topic create tweets --partitions 1 --replication 1`. Bei erfolgreicher Erstellung erscheint auf der Kommandozeile folgende Rückmeldung:

```
{
  "message": "Output: Created topic \"tweets\".\n"
}
```

## Endpunkte anzeigen
Mit `dcos kafka connection` ließen sich in früheren Versionen die Endpunkte der verschiedenen Broker anzeigen. Inzwischen lassen sich die konfigurierten Broker nur noch über das Kommando `dcos kafka broker list` auflisten. Als Resultat erscheint eine Liste mit den konfigurierten Broker IDs. Mit `dcos kafka broker get <ID>` lassen sich die konfigurierten Endpunkte des Brokers anzeigen. Die Angaben aus diesem Listing werden benötigt, um einen Producer mit diesem Endpunkt zu verbinden.

```
MacBook-NBV51:Downloads BjoernBerg$ dcos kafka broker get 0
{
  "listener_security_protocol_map": {
    "PLAINTEXT": "PLAINTEXT"
  },
  "endpoints": [
    "PLAINTEXT://10.0.3.28:1025"
  ],
  "jmx_port": -1,
  "port": 1025,
  "host": "10.0.3.28",
  "version": 4,
  "timestamp": "1524303289127"
}
```
