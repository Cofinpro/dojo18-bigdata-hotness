package de.cofinpro.dojo18.bigdata.kafkaproducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

/**
 * Created by David Olah on 20.04.2018.
 */
public class CsvToKafkaProducer {

    private static final String TOPIC_NAME_FOR_TWEETS = "tweets";
    private Logger logger = LoggerFactory.getLogger(getClass());

    public void produceFromCsvFile(File file, CsvToTwitterDataMapping mapping, Producer<String, String> kafkaProducer) throws IOException {
        Reader in = new FileReader(file);
        CSVFormat csvFormat = CSVFormat.EXCEL
                .withDelimiter(';')
                .withFirstRecordAsHeader();
        Iterable<CSVRecord> records = csvFormat.parse(in);
        for (CSVRecord csvRecord : records) {
            String id = getValueFromRecordForTweetContentWithMapping(csvRecord, TweetContent.ID, mapping).trim();
            String username = getValueFromRecordForTweetContentWithMapping(csvRecord, TweetContent.USER, mapping);
            String content = getValueFromRecordForTweetContentWithMapping(csvRecord, TweetContent.CONTENT, mapping);

            KafkaTweet kafkaTweet = new KafkaTweet();
            kafkaTweet.setId(id);
            kafkaTweet.setUser(username);
            kafkaTweet.setContent(content);

            logger.trace("Sending {} to Kafka-Topic = {}", kafkaTweet, TOPIC_NAME_FOR_TWEETS);
            String json = buildJsonForKafkaTweet(kafkaTweet);
            kafkaProducer.send(new ProducerRecord<>(TOPIC_NAME_FOR_TWEETS, json));
        }
    }

    String getValueFromRecordForTweetContentWithMapping(CSVRecord csvRecord, TweetContent tweetContent, CsvToTwitterDataMapping mapping) {
        Optional<Integer> columnIndex = mapping.getColumnIndexForTweetContent(tweetContent);
        return columnIndex.isPresent() ? csvRecord.get(columnIndex.get()) : "";
    }

    String buildJsonForKafkaTweet(KafkaTweet kafkaTweet) {
        String json = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            json = objectMapper.writeValueAsString(kafkaTweet);
        } catch (JsonProcessingException e) {
            // nop
        }
        return json;
    }
}