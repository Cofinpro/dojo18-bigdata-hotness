#!/usr/bin/env sh
# Variablen zur Beschreibung der Knoten
node1=cassandra1
node2=cassandra2

function start() {
    echo "Starte einen Cluster-Verbund"
    docker run --name $node1 -d cassandra:latest
    docker run --name $node2 -d --link $node1:cassandra cassandra:latest
    
    sleep 10

    echo "Frage Status des Clusters ab"
    docker exec -i -t cassandra1 sh -c 'nodetool status'
}

function stop() {
    docker stop $node1 $node2
    docker rm $node1 $node2
}

case $1 in 
    start)
        start
        ;;

    stop)
        stop
        ;;
    *)
        echo "$1 kenne ich nicht"
        ;;
esac    




