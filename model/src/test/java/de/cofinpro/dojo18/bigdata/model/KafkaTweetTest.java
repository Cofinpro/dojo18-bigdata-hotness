package de.cofinpro.dojo18.bigdata.model;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created by David Olah on 21.04.2018.
 */
public class KafkaTweetTest {
    @Test
    public void toJson() {
        KafkaTweet kafkaTweet = new KafkaTweet();
        kafkaTweet.setUser("awesomeUser");
        kafkaTweet.setId("1285");
        kafkaTweet.setContent("I am a tweet content");

        String json = kafkaTweet.toJson();

        assertThat(json, is("{\"id\":\"1285\",\"user\":\"awesomeUser\",\"content\":\"I am a tweet content\"}"));
    }

    @Test
    public void fromJson() {
        String jsonString = "{\"id\":\"1285\",\"user\":\"awesomeUser\",\"content\":\"I am a tweet content\"}";
        Optional<KafkaTweet> kafkaTweet = KafkaTweet.fromJson(jsonString);
        assertThat(kafkaTweet.isPresent(), is(true));
        assertThat(kafkaTweet.get().getUser(), is("awesomeUser"));
        assertThat(kafkaTweet.get().getId(), is("1285"));
        assertThat(kafkaTweet.get().getContent(), is("I am a tweet content"));
    }
}