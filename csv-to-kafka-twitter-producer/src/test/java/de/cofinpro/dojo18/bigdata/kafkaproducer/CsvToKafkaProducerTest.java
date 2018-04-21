package de.cofinpro.dojo18.bigdata.kafkaproducer;

import de.cofinpro.dojo18.bigdata.model.KafkaTweet;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by David Olah on 20.04.2018.
 */
public class CsvToKafkaProducerTest {
    private CsvToKafkaProducer csvToKafkaProducer = new CsvToKafkaProducer();

    @Test
    public void buildJsonForKafkaTweet() throws Exception {
        KafkaTweet kafkaTweet = new KafkaTweet();
        kafkaTweet.setUser("awesomeUser");
        kafkaTweet.setId("1285");
        kafkaTweet.setContent("I am a tweet content");

        String json = csvToKafkaProducer.buildJsonForKafkaTweet(kafkaTweet);

        assertThat(json, is("{\"id\":\"1285\",\"user\":\"awesomeUser\",\"content\":\"I am a tweet content\"}"));
    }

}