package com.example.avrokafka.producer;

import com.example.avrokafka.common.Message;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Producer {

    public static void main(String[] args) throws IOException {

        System.out.println("Starting producer...");

        Properties props = new Properties();
        props.put("bootstrap.servers", "broker:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");

        org.apache.kafka.clients.producer.Producer<String, byte[]> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++) {

            Message msg = Message.newBuilder().setUser("Honza").setMessage("Ahoj Honzo, tohle je zkouska c. " + i).build();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
            DatumWriter<Message> writer = new SpecificDatumWriter<Message>(Message.getClassSchema());
            writer.write(msg, encoder);
            encoder.flush();
            out.close();

            System.out.println("Sending: " + msg.toString());
            producer.send(new ProducerRecord<String, byte[]>(
                    "foo", out.toByteArray()
            ));
        }
//            producer.send(new ProducerRecord<String, String>("foo", Integer.toString(i), Integer.toString(i)));

        System.out.println("Closing producer...");
        producer.close();
        System.out.println("Bye...");
    }
}
