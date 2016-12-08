package com.mq.kafka;

/**
 * @author kelong
 * @date 3/7/16
 */
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class TestProducer {
    public static void main(String[] args) {
        Random rnd = new Random();

        Properties props = new Properties();
        props.put("metadata.broker.list", "localhost:9092,172.26.50.65:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("partitioner.class", "com.mq.kafka.SimplePartitioner");
        props.put("request.required.acks", "1");

        ProducerConfig config = new ProducerConfig(props);

        Producer<String, String> producer = new Producer<String, String>(config);

        for (long nEvents = 0; nEvents < 10; nEvents++) {
            long runtime = new Date().getTime();
            String ip = "192.168.2." + rnd.nextInt(255);
            String msg = runtime + "www.example.com,"+ip;
            KeyedMessage<String, String> data = new KeyedMessage<String, String>("test1", ip, msg);
            producer.send(data);
        }
        producer.close();
    }
}
