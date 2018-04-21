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
        kafkaTweet.setIdStr("1285");
        kafkaTweet.setContent("I am a tweet content");

        String json = kafkaTweet.toJson();

        assertThat(json, is("{\"id_str\":\"1285\",\"user\":\"awesomeUser\",\"text\":\"I am a tweet content\"}"));
    }

    @Test
    public void fromJson() {
        String jsonString = "{\"id_str\":\"1285\",\"user\":\"awesomeUser\",\"text\":\"I am a tweet content\"}";
        Optional<KafkaTweet> kafkaTweet = KafkaTweet.fromJson(jsonString);
        assertThat(kafkaTweet.isPresent(), is(true));
        assertThat(kafkaTweet.get().getUser(), is("awesomeUser"));
        assertThat(kafkaTweet.get().getIdStr(), is("1285"));
        assertThat(kafkaTweet.get().getContent(), is("I am a tweet content"));
    }

    @Test
    public void fromRealTwitterFeedJson() {
        String twitterJson = "{\"created_at\":\"Sat Apr 21 15:58:04 +0000 2018\",\"id\":987722142430236672,\"id_str\":\"987722142430236672\",\"text\":\"Clara y merecida victoria en Hannover. \\ud83d\\udc4f #H96FCB\\n\\nLa cr\\u00f3nica \\ud83d\\udcdd\\n\\n\\u27a1 https:\\/\\/t.co\\/RhCRu6HTRk https:\\/\\/t.co\\/7vEWdDt0vi\",\"display_text_range\":[0,89],\"source\":\"\\u003ca href=\\\"https:\\/\\/about.twitter.com\\/products\\/tweetdeck\\\" rel=\\\"nofollow\\\"\\u003eTweetDeck\\u003c\\/a\\u003e\",\"truncated\":false,\"in_reply_to_status_id\":null,\"in_reply_to_status_id_str\":null,\"in_reply_to_user_id\":null,\"in_reply_to_user_id_str\":null,\"in_reply_to_screen_name\":null,\"user\":{\"id\":2152691378,\"id_str\":\"2152691378\",\"name\":\"FC Bayern Espa\\u00f1ol\",\"screen_name\":\"FCBayernES\",\"location\":null,\"url\":null,\"description\":\"Twitter oficial del FC Bayern M\\u00fcnchen en espa\\u00f1ol #MiaSanMia. Deutsch @FCBayern | English @FCBayernEN | USA @FCBayernUS | \\u00c1rabe @FCBayernAr http:\\/\\/fcbayern.de\\/es\",\"translator_type\":\"none\",\"protected\":false,\"verified\":true,\"followers_count\":489212,\"friends_count\":34,\"listed_count\":1311,\"favourites_count\":6664,\"statuses_count\":38264,\"created_at\":\"Thu Oct 24 10:26:27 +0000 2013\",\"utc_offset\":10800,\"time_zone\":\"Athens\",\"geo_enabled\":true,\"lang\":\"es\",\"contributors_enabled\":false,\"is_translator\":false,\"profile_background_color\":\"000000\",\"profile_background_image_url\":\"http:\\/\\/pbs.twimg.com\\/profile_background_images\\/378800000100986561\\/2524575a3fc408f2f78866dbcff4b5d7.jpeg\",\"profile_background_image_url_https\":\"https:\\/\\/pbs.twimg.com\\/profile_background_images\\/378800000100986561\\/2524575a3fc408f2f78866dbcff4b5d7.jpeg\",\"profile_background_tile\":false,\"profile_link_color\":\"B30009\",\"profile_sidebar_border_color\":\"000000\",\"profile_sidebar_fill_color\":\"DDEEF6\",\"profile_text_color\":\"333333\",\"profile_use_background_image\":true,\"profile_image_url\":\"http:\\/\\/pbs.twimg.com\\/profile_images\\/874187694242451456\\/LL4WwvTm_normal.jpg\",\"profile_image_url_https\":\"https:\\/\\/pbs.twimg.com\\/profile_images\\/874187694242451456\\/LL4WwvTm_normal.jpg\",\"profile_banner_url\":\"https:\\/\\/pbs.twimg.com\\/profile_banners\\/2152691378\\/1524236700\",\"default_profile\":false,\"default_profile_image\":false,\"following\":null,\"follow_request_sent\":null,\"notifications\":null},\"geo\":null,\"coordinates\":null,\"place\":null,\"contributors\":null,\"is_quote_status\":false,\"quote_count\":0,\"reply_count\":0,\"retweet_count\":0,\"favorite_count\":0,\"entities\":{\"hashtags\":[{\"text\":\"H96FCB\",\"indices\":[41,48]}],\"urls\":[{\"url\":\"https:\\/\\/t.co\\/RhCRu6HTRk\",\"expanded_url\":\"http:\\/\\/fc.bayern\\/CronicaH96FCB\",\"display_url\":\"fc.bayern\\/CronicaH96FCB\",\"indices\":[66,89]}],\"user_mentions\":[],\"symbols\":[],\"media\":[{\"id\":987722010682970112,\"id_str\":\"987722010682970112\",\"indices\":[90,113],\"media_url\":\"http:\\/\\/pbs.twimg.com\\/media\\/DbUX73-X0AA5WRV.jpg\",\"media_url_https\":\"https:\\/\\/pbs.twimg.com\\/media\\/DbUX73-X0AA5WRV.jpg\",\"url\":\"https:\\/\\/t.co\\/7vEWdDt0vi\",\"display_url\":\"pic.twitter.com\\/7vEWdDt0vi\",\"expanded_url\":\"https:\\/\\/twitter.com\\/FCBayernES\\/status\\/987722142430236672\\/photo\\/1\",\"type\":\"photo\",\"sizes\":{\"small\":{\"w\":680,\"h\":453,\"resize\":\"fit\"},\"thumb\":{\"w\":150,\"h\":150,\"resize\":\"crop\"},\"large\":{\"w\":2048,\"h\":1365,\"resize\":\"fit\"},\"medium\":{\"w\":1200,\"h\":800,\"resize\":\"fit\"}}}]},\"extended_entities\":{\"media\":[{\"id\":987722010682970112,\"id_str\":\"987722010682970112\",\"indices\":[90,113],\"media_url\":\"http:\\/\\/pbs.twimg.com\\/media\\/DbUX73-X0AA5WRV.jpg\",\"media_url_https\":\"https:\\/\\/pbs.twimg.com\\/media\\/DbUX73-X0AA5WRV.jpg\",\"url\":\"https:\\/\\/t.co\\/7vEWdDt0vi\",\"display_url\":\"pic.twitter.com\\/7vEWdDt0vi\",\"expanded_url\":\"https:\\/\\/twitter.com\\/FCBayernES\\/status\\/987722142430236672\\/photo\\/1\",\"type\":\"photo\",\"sizes\":{\"small\":{\"w\":680,\"h\":453,\"resize\":\"fit\"},\"thumb\":{\"w\":150,\"h\":150,\"resize\":\"crop\"},\"large\":{\"w\":2048,\"h\":1365,\"resize\":\"fit\"},\"medium\":{\"w\":1200,\"h\":800,\"resize\":\"fit\"}}}]},\"favorited\":false,\"retweeted\":false,\"possibly_sensitive\":false,\"filter_level\":\"low\",\"lang\":\"es\",\"timestamp_ms\":\"1524326284402\"}";
        Optional<KafkaTweet> kafkaTweet = KafkaTweet.fromJson(twitterJson);
        assertThat(kafkaTweet.isPresent(), is(true));
        assertThat(kafkaTweet.get().getIdStr(), is("987722142430236672"));
        assertThat(kafkaTweet.get().getUser(), is("FCBayernES"));
        assertThat(kafkaTweet.get().getContent(), is("Clara y merecida victoria en Hannover. \uD83D\uDC4F #H96FCB\n" +
                "\n" +
                "La crónica \uD83D\uDCDD\n" +
                "\n" +
                "➡ https://t.co/RhCRu6HTRk https://t.co/7vEWdDt0vi"));
    }
}