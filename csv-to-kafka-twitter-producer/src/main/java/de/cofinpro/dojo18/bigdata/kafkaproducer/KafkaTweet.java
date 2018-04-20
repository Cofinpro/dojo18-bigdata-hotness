package de.cofinpro.dojo18.bigdata.kafkaproducer;

/**
 * Created by David Olah on 20.04.2018.
 */
public class KafkaTweet {
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
}
