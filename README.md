Kafka + AVRO example 
====================

Compile
-------

`gradle clean build jar`

Run
---

*Consumer:* `java -jar consumer/build/libs/consumer-1.0-SNAPSHOT.jar` 
*Producer:* `java -jar producer/build/libs/producer-1.0-SNAPSHOT.jar` 

AVRO
----

Class com.example.avrokafka.Message is generated from common/src/main/resources/Message.avsc schema using Avro Tools:

`/path/to/avro-tools.jar compile schema common/src/main/resources/Message.avsc common/src/main/java/`
