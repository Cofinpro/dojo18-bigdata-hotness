package spark;

import de.cofinpro.dojo18.bigdata.model.KafkaTweet;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ReadFromTweetsKafkaTest {

    @Test
    public void setPosNegTest() {
        ReadFromTweetsKafka readFromTweetsKafka = new ReadFromTweetsKafka();

        List<String> positive = new ArrayList<>();
        List<String> negative = new ArrayList<>();

        KafkaTweet kafkaTweet = new KafkaTweet();
        kafkaTweet.setContent("text");
        kafkaTweet.setIdStr("3453425432634");
        kafkaTweet.setUser("TestUser");

        KafkaTweet kafkaTweet2 = new KafkaTweet();
        kafkaTweet.setContent("text");
        kafkaTweet.setIdStr("3453425432634");
        kafkaTweet.setUser("TestUser");

        try {
            positive = Files.readAllLines(Paths.get("positive-words.txt"));
            negative = Files.readAllLines(Paths.get("negative-words.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        KafkaTweet kafkaTweetTest = readFromTweetsKafka.setPosNeg(positive, negative, kafkaTweet);

        assertEquals(kafkaTweetTest.getNegative(), 0);


        KafkaTweet kafkaTweetTest2 = readFromTweetsKafka.setPosNeg(positive, negative, kafkaTweet);

        assertEquals(kafkaTweetTest2.getNegative(), 0);
        assertEquals(kafkaTweetTest2.getPositive(), 0);

    }

}