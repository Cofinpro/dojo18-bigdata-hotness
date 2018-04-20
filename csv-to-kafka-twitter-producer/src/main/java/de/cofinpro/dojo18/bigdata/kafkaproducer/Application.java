package de.cofinpro.dojo18.bigdata.kafkaproducer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by David Olah on 20.04.2018.
 */
public class Application {
    private static final String NAME_OF_CSV_FILE = "dashboard_x_usa_x_filter_nativeretweets.csv";

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> kafkaProducer = new KafkaProducer<>(props);

        File file = new File(Application.class.getClassLoader().getResource(NAME_OF_CSV_FILE).getFile());
        CsvToTwitterDataMapping mapping = CsvToTwitterDataMapping.newBuilder()
                .addMapping(TweetContent.ID, 0)
                .addMapping(TweetContent.USER, 4)
                .addMapping(TweetContent.CONTENT, 6)
                .build();
        CsvToKafkaProducer csvToKafkaProducer = new CsvToKafkaProducer();
        csvToKafkaProducer.produceFromCsvFile(file, mapping, kafkaProducer);

        kafkaProducer.close();
    }
}
