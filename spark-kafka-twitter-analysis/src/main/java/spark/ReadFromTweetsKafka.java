package spark;

import de.cofinpro.dojo18.bigdata.model.KafkaTweet;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.util.control.Exception;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        JavaStreamingContext streamingContext = new JavaStreamingContext(javaSparkContext, Durations.seconds(10L));

        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "localhost:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "use_a_separate_group_id_for_each_stream");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);

        Collection<String> topics = Collections.singletonList("BVBB04");

        JavaInputDStream<ConsumerRecord<String, String>> stream =
                KafkaUtils.createDirectStream(
                        streamingContext,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.Subscribe(topics, kafkaParams)
                );

        JavaDStream<Optional<KafkaTweet>> streamOfKafkaTweetOptionals = stream.map(record -> KafkaTweet.fromJson(record.value()));
        streamOfKafkaTweetOptionals = streamOfKafkaTweetOptionals.filter(Optional::isPresent);
        JavaDStream<KafkaTweet> streamOfKafkaTweets = streamOfKafkaTweetOptionals.map(Optional::get);



        Scanner s = null;
        try {
            s = new Scanner(new File(ReadFromTweetsKafka.class.getClassLoader().getResource("positive-words.txt").getFile()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> positive = new ArrayList<String>();
        while (s.hasNext()){
            positive.add(s.next());
        }
        s.close();

        Scanner s1 = null;
        try {
            s1 = new Scanner(new File(ReadFromTweetsKafka.class.getClassLoader().getResource("negative-words.txt").getFile()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> negative = new ArrayList<String>();
        while (s1.hasNext()){
            negative.add(s1.next());
        }
        s1.close();




        List<String> finalPositive = positive;
        List<String> finalNegative = negative;


        streamOfKafkaTweets = streamOfKafkaTweets.map(
                new Function<KafkaTweet, KafkaTweet>() {
            @Override
            public KafkaTweet call(KafkaTweet record) throws java.lang.Exception {
                System.out.println("test");
                return setPosNeg(finalPositive, finalNegative, record);
            }
        });

        streamOfKafkaTweets.print();

        streamingContext.start();
        streamingContext.awaitTermination();
    }

    public static KafkaTweet setPosNeg(List<String> finalPositive, List<String> finalNegative, KafkaTweet record) {
        String regex = "([^a-zA-Z']+)'*\\1*";
        String[] split = record.getContent().split(regex);


        int posCount = 0;
        int negCount = 0;

        for(int i = 0; i < split.length; i++) {
            split[i] = split[i].replaceAll("\\s+","");
            if(finalPositive.contains(split[i].toLowerCase())) {
                posCount++;
            }
            if(finalNegative.contains(split[i].toLowerCase())) {
                negCount++;
            }
        }

        record.setPositive(posCount);
        record.setNegative(negCount);

        return record;
    }
}
