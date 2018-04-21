package de.cofinpro.dojo18.bigdata.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

/**
 * Created by David Olah on 21.04.2018.
 */
@JsonPropertyOrder({"id_str", "user", "content"})
public class KafkaTweet implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(KafkaTweet.class);

    @JsonProperty("id_str")
    String idStr;

    @JsonProperty("user")
    String user;

    @JsonProperty("text")
    String content;

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
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
                "idStr='" + idStr + '\'' +
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
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            KafkaTweet kafkaTweet = objectMapper.readValue(jsonString, KafkaTweet.class);
            result = Optional.of(kafkaTweet);
        } catch (IOException e) {
            logger.warn("Unmarshalling failed - maybe it is a real twitter json?", e);
            logger.info("Attempting to unmarshall based on twitter json with cusom deserialization");
            objectMapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(KafkaTweet.class, new TwitterJsonDeserializer());
            objectMapper.registerModule(module);
            try {

                KafkaTweet kafkaTweet = objectMapper.readValue(jsonString, KafkaTweet.class);
                result = Optional.of(kafkaTweet);
            } catch (IOException e1) {
                logger.error("Could not unmarshal from twitter json", e1);
            }
        }
        return result;
    }

    private static class TwitterJsonDeserializer extends JsonDeserializer<KafkaTweet> {

        @Override
        public KafkaTweet deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);

            String idStr = node.get("id_str").asText();
            String content = node.get("text").asText();

            JsonNode userNode = node.get("user");
            String userName = userNode.get("screen_name").asText();

            KafkaTweet kafkaTweet = new KafkaTweet();
            kafkaTweet.setIdStr(idStr);
            kafkaTweet.setUser(userName);
            kafkaTweet.setContent(content);

            return kafkaTweet;
        }
    }
}
