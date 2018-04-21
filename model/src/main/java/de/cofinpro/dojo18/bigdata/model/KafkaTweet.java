package de.cofinpro.dojo18.bigdata.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

/**
 * Created by David Olah on 21.04.2018.
 */
public class KafkaTweet implements Serializable {
    String id;
    String user;
    String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "KafkaTweet{" +
                "id='" + id + '\'' +
                ", user='" + user + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String toJson() {
        String json = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            json = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            // nop
        }
        return json;
    }

    public static Optional<KafkaTweet> fromJson(String jsonString) {
        Optional<KafkaTweet> result = Optional.empty();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            KafkaTweet kafkaTweet = objectMapper.readValue(jsonString, KafkaTweet.class);
            result = Optional.of(kafkaTweet);
        } catch (IOException e) {
            // nop
        }
        return result;
    }
}
