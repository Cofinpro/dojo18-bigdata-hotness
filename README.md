# dojo18-bigdata-hotness

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
