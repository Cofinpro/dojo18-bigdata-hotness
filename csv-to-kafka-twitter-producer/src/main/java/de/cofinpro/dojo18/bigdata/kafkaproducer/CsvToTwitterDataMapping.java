package de.cofinpro.dojo18.bigdata.kafkaproducer;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Optional;

/**
 * Created by David Olah on 20.04.2018.
 */
public class CsvToTwitterDataMapping {
    private Map<TweetContent, Integer> mappings;

    private CsvToTwitterDataMapping(Map<TweetContent, Integer> mappings) {
        this.mappings = new HashMap<>(mappings);
    }

    public Optional<Integer> getColumnIndexForTweetContent(TweetContent tweetContent) {
        return Optional.ofNullable(mappings.get(tweetContent));
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Map<TweetContent, Integer> builderMappings = new HashMap<>();

        public CsvToTwitterDataMapping build() {
            CsvToTwitterDataMapping mappingObject = new CsvToTwitterDataMapping(builderMappings);
            builderMappings = new HashMap<>();
            return mappingObject;
        }

        public Builder addMapping(TweetContent tweetContent, int columnIndex) {
            builderMappings.put(tweetContent, columnIndex);
            return this;
        }
    }

}
