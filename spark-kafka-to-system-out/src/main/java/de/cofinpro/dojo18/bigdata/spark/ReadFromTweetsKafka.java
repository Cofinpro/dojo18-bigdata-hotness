package de.cofinpro.dojo18.bigdata.spark;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

import java.util.*;

/**
 * Created by David Olah on 20.04.2018.
 * Stolen from: https://de.hortonworks.com/tutorial/setting-up-a-spark-development-environment-with-java/
 */
public class ReadFromTweetsKafka {
    private static final Logger logger = LoggerFactory.getLogger(ReadFromTweetsKafka.class);

    public static void main(String[] args) throws InterruptedException {
        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("ReadTweetsKafka");

        // Create a Java version of the Spark Context
        JavaSparkContext javaSparkContext = new JavaSparkContext(conf);
        JavaStreamingContext streamingContext = new JavaStreamingContext(javaSparkContext, Durations.seconds(1L));

        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "localhost:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "use_a_separate_group_id_for_each_stream");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);

        Collection<String> topics = Collections.singletonList("tweets");

        JavaInputDStream<ConsumerRecord<String, String>> stream =
                KafkaUtils.createDirectStream(
                        streamingContext,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.Subscribe(topics, kafkaParams)
                );

        logger.info("Now I have a stream object with {}....and now??");

        stream.map(record -> record.value()).print();

        streamingContext.start();
        streamingContext.awaitTermination();
    }
}
