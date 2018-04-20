# Evaluation DC/OS
Im SMACK-Stack wird das M durch DC/OS repräsentiert.

DC/OS soll die einfache Verwaltung und Installation von Paketen und Services (Anwendungen) vereinfachen
und dient gleichzeitig dazu, den Cluster zu verwalten.

Pakete und Services können einfach über die DC/OS CLI gemanagt und installiert werden.

## Basispakete über Repository installieren
Pakete werden dabei über ein internes Repository von DC/OS installiert. Die Firma Mesosphere hinter
DC/OS verwaltet ein zentrales Repository vergleichbar mit Maven oder Gradle die fertig zu provisionierende
Pakete aus dem SMACK-Stack bspw. Cassandra enthalten.

Durch die Provisionierung über DC/OS werden die Pakete direkt auf alle angebundenen Knoten (Agents)
installiert.

Sofern das Repository erreichbar ist, lassen sich die Pakete über `dcos package install` installieren.
Eine weitere Voraussetzung laut der [zentralen Quellen](https://docs.mesosphere.com/services/cassandra/)
ist, dass die Enterprise CLI installiert und ein Schlüssel zur Authentifizierung vorhanden sind.

Für die Evaluation kam aktuell [DC/OS Vagrant](https://github.com/dcos/dcos-vagrant) zum Einsatz. Die 
Version ist beschränkt und enthält keine Enterprise CLI.
