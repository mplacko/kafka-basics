# kafka-basics
An example for explaining basics of streaming via Apache Kafka with simple producer and consumer.

### HOW TO CONFIGURE THE PROJECT
path: /kafka-basics/src/main/resources/
- `change bootstrap-server(s) in client.properties: bootstrap.servers.config=<bootstrap-server_a>:9093,<bootstrap-server_x>:9093`

## Building and Running

### Build
To build the application it is required to have this installed:
- `Java 9`
- `Maven 3.x`

Then just run this:
```sh
mvn clean install assembly:single
```

### Run
- `$ su <hdfs-user>`
- `$ kinit -kt /etc/security/keytabs/<hdfs-user>.keytab <hdfs-user>`
- `$ cd /home/<user>`
- `$ chmod 770 ./kafka/kafka-basics-1.0-jar-with-dependencies.jar`
- `$ chown <user>:<user> ./kafka/kafka-basics-1.0-jar-with-dependencies.jar`
- `$ chmod 770 ./kafka/kafka_run_topics.sh`
- `$ chown <user>:<user> ./kafka/kafka_run_topics.sh`
- `$ sh ./kafka/kafka_run_topics.sh`
- `$ chmod 770 ./kafka/kafka_run_producer.sh`
- `$ chown <user>:<user> ./kafka/kafka_run_producer.sh`
- `$ sh ./kafka/kafka_run_producer.sh`
- `$ chmod 770 ./kafka/kafka_run_consumer.sh`
- `$ chown <user>:<user> ./kafka/kafka_run_consumer.sh`
- `$ sh ./kafka/kafka_run_consumer.sh`
