# Anwendungen erstellen
Anwendungen sind lang-laufende **Services** mit mehreren Instanzen auf verschiedenen bzw. mehreren Hosts. Eine einzelne Instanz einer Anwendung wird als **Task** bezeichnet.

Eine Anwendung wird in Form von JSON-Scripten beschrieben, die eine Aktion ausführen oder Ressourcen zur Verfügung stellen. Die über das Paket-Repository verfügbaren Services verfolgen in DC/OS exakt dieses Prinzip. Alle Services bestehen primär aus JSON-Beschreibungen:

```
{
    "id": "basic-app", 
    "cmd": "while [ true ] ; do echo 'Hello Marathon' ; sleep 5 ; done",
    "cpus": 0.1,
    "mem": 32,
    "instances": 1
}
```

Im Beispiel handelt es sich um eine einfache Kommandozeilenanwendung, die alle 5 Sekunden `Hello Marathon` auf der Kommandozeile ausgibt.
