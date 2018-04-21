from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream
from kafka import SimpleProducer, KafkaClient

access_token = "374527580-KZx5Xr7yHdiG3QtEqkQmT3wUg2uS6RQgvOo2Dd4N"
access_token_secret =  "cPBNhI8OqyLj0nSh0VFfAgIztNIhWMMi7tnyxZwuvdZ1v"
consumer_key =  "y025jxSQOFVdwdSPfTRjUNRdJ"
consumer_secret =  "ANRM4lG1C1bXWAwiJ3uyLhBLE8rUSFn9xtmBhaTXvJxnllT87c"

class StdOutListener(StreamListener):
    def on_data(self, data):
        producer.send_messages("BVBB04", data.encode('utf-8'))
        print (data)
        return True
    def on_error(self, status):
        print (status)

kafka = KafkaClient("localhost:9092")
producer = SimpleProducer(kafka)
l = StdOutListener()
auth = OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)
stream = Stream(auth, l)
stream.filter(track=['#BVBB04'])