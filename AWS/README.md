# Setup DC/OS and SMACK-Stack in the cloud with Amazon Web Services

## AWS Cloud Formation Templates

DC/OS bietet für diverse Cloud-Umgebungen bereits [Templates](https://downloads.dcos.io/dcos/stable/aws.html) zum Setup eines DC/OS gemanagten Clusters.

Diese lassen sich direkt in der AWS CloudFormation Management Console laden und daraus ein full-blown DC/OS Stack mit Controller-Node und 1-n Master-Slaves hochziehen.

Im Dojo wurde einen Stack mit folgenden Dimensionen aufgesetzt:
* 1 DC/OS Master Node
* 1 Mesos Master (Public Slave)
* 5 Mesos Agents (Private Slaves)

Dazu haben wir das AWS Cloud Formation Template [dcos.cloudformation.json](dcos.cloudformation.json) verwendet, mit folgenden Parametern:
* EC2 Key Name (vorab muss ein EC2 Key-Pair in EC2 erzeugt werden, über das anschließend der SSH-Zugriff auf das Cluster abgesichert wird)
* 1 Public Slave
* 5 Private Slaves

## In DC/OS die SMACK Services installieren


## DC/OS command line lokal einrichten

Im DC/OS Webinterface oben links auf den Cluster-Namen klicken, dann auf _Install CLI_ und den Anweisungen folgen.
Dadurch wird auf dem lokalen Rechner eine leichtgewichtige CLI installiert, die die Interaktion mit dem DC/OS Master erlaubt.

## Node failure & replacement bei Cassandra

Simulierter Ausfall eines Knotens:
* In der EC2 Konsole bei AWS wird ein Agent Node manuell _gekillt_, um einen Ausfall zu simulieren:
  1. EC2 Konsole --> Instances --> _-Slave Instanz auswählen-_
  1. Menü: `Actions --> Instance State --> Destroy`
  1. Knoten fällt nun raus; durch die im Cloud Formation Template (s.o.) festgelegte _Auto Scaling Group_ wird der Ausfall in wenigen Sekunden erkannt und eine neue EC2 Instanz provisioniert und hochgefahren.
  1. Nach einigen Minuten (durchaus 10-15min) ist der neue Agent Node im DC/OS Mesos-Cluster verfügbar und wird von diesem als Slave-Node für die Verteilung von Tasks eingebunden
* *Problem:* Cassandra selbst wird nicht einfach von selbst auf neue Knoten "verteilt", wenn einer der Cassandra Cluster-Knoten nicht mehr erreichbar ist - es könnte ja durchaus auch an einem vorübergehenden Problem liegen. Man muss der Cassandra-DC/OS-Instanz erst mitteilen, dass der Knoten ersetzt werden soll:
  1. Vereinfacht wird dies mit dem DC/OS cassandra CLI interface - dieses muss man zunächst (lokal) installieren: `dcos package install cassandra --cli`
  1. Anschließend bekommt man mit `dcos cassandra pod status` einen Überblick über die Nodes und findet auch schnell den Ausgefallenen. Beispiel:
    ```
    $ dcos cassandra pod status
      cassandra
      └─ node
        ├─ node-0
        │  ├─ node-0-init_system_keyspaces (FINISHED)
        │  ├─ node-0-repair (<UNKNOWN>)
        │  └─ node-0-server (RUNNING)
        ├─ node-1
        │  ├─ node-1-repair (<UNKNOWN>)
        │  └─ node-1-server (RUNNING)
        ├─ node-2
        │  ├─ node-2-repair (<UNKNOWN>)
        │  └─ node-2-server (RUNNING)
        ├─ node-3
        │  ├─ node-3-repair (<UNKNOWN>)
        │  └─ node-3-server (RUNNING)
        └─ node-4
            ├─ node-4-repair (<UNKNOWN>)
            └─ node-4-server (LOST)
    ```
  1. Knoten *node-4* muss nun ersetzt werden: `dcos cassandra pod replace node-4`
  1. Nun wird durch DC/OS ein weiterer Agent Node (einer, auf dem noch kein Cassandra Server läuft) als neuer "node-4" eingerichtet - dauert einige wenige Minuten