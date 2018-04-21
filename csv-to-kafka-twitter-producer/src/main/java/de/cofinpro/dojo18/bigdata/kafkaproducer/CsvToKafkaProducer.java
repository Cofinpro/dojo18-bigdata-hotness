package de.cofinpro.dojo18.bigdata.kafkaproducer;

import de.cofinpro.dojo18.bigdata.model.KafkaTweet;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Optional;

/**
 * Created by David Olah on 20.04.2018.
 */
public class CsvToKafkaProducer {

    private static final String TOPIC_NAME_FOR_TWEETS = "tweets";
    private Logger logger = LoggerFactory.getLogger(getClass());

    public Iterable<CSVRecord> createRecordsFromCsvFile(InputStream is) throws IOException {
        logger.info("Creating records from CSV-File");
        CSVFormat csvFormat = CSVFormat.EXCEL
                .withDelimiter(';')
                .withFirstRecordAsHeader();
        return csvFormat.parse(new InputStreamReader(is));
    }

    public Iterable<CSVRecord> createRecordsFromCsvFile(File file) throws IOException {
        logger.info("Creating records from CSV-File");
        Reader in = new FileReader(file);
        CSVFormat csvFormat = CSVFormat.EXCEL
                .withDelimiter(';')
                .withFirstRecordAsHeader();
        return csvFormat.parse(in);
    }

    public void sendRecordsToKafka(Iterable<CSVRecord> records, CsvToTwitterDataMapping mapping, Producer<String, String> kafkaProducer) {
        logger.info("Starting to send records to kafka");
        long count = 0;
        for (CSVRecord csvRecord : records) {
            String id = getValueFromRecordForTweetContentWithMapping(csvRecord, TweetContent.ID, mapping).trim();
            String username = getValueFromRecordForTweetContentWithMapping(csvRecord, TweetContent.USER, mapping);
            String content = getValueFromRecordForTweetContentWithMapping(csvRecord, TweetContent.CONTENT, mapping);

            KafkaTweet kafkaTweet = new KafkaTweet();
            kafkaTweet.setIdStr(id);
            kafkaTweet.setUser(username);
            kafkaTweet.setContent(content);

            logger.trace("Sending {} to Kafka-Topic = {}", kafkaTweet, TOPIC_NAME_FOR_TWEETS);
            String json = kafkaTweet.toJson();
            kafkaProducer.send(new ProducerRecord<>(TOPIC_NAME_FOR_TWEETS, json));
            count++;
        }

        logger.info("Sent {} records to kafka", count);
    }

    String getValueFromRecordForTweetContentWithMapping(CSVRecord csvRecord, TweetContent tweetContent, CsvToTwitterDataMapping mapping) {
        Optional<Integer> columnIndex = mapping.getColumnIndexForTweetContent(tweetContent);
        return columnIndex.isPresent() ? csvRecord.get(columnIndex.get()) : "";
    }
}
