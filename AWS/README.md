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
