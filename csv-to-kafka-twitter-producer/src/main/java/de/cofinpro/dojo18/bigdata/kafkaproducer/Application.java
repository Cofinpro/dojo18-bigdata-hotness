package de.cofinpro.dojo18.bigdata.kafkaproducer;

import org.apache.commons.cli.*;
import org.apache.commons.csv.CSVRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

/**
 * Created by David Olah on 20.04.2018.
 */
public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class);
    private static final String NAME_OF_CSV_FILE = "dashboard_x_usa_x_filter_nativeretweets.csv";
    private static final int RESEND_ITERATIONS = 100000;

    public static void main(String[] args) throws IOException, InterruptedException {
        logger.info("Starting application");

        CommandLine commandLine = getCommandLine(args);
        Properties props = initProperties(commandLine);

        Producer<String, String> kafkaProducer = new KafkaProducer<>(props);

        CsvToTwitterDataMapping mapping = CsvToTwitterDataMapping.newBuilder()
                .addMapping(TweetContent.ID, 0)
                .addMapping(TweetContent.USER, 4)
                .addMapping(TweetContent.CONTENT, 6)
                .build();

        CsvToKafkaProducer csvToKafkaProducer = new CsvToKafkaProducer();
        for (int i = 0; i < RESEND_ITERATIONS; i++) { // TODO: make this configurable
            logger.info("Iteration {} of {}", i+1, RESEND_ITERATIONS);
            InputStream is = Application.class.getClassLoader().getResourceAsStream(NAME_OF_CSV_FILE);
            Iterable<CSVRecord> records = csvToKafkaProducer.createRecordsFromCsvFile(is);
            csvToKafkaProducer.sendRecordsToKafka(records, mapping, kafkaProducer);
            logger.info("Waiting 5 seconds until re-sending..");
            Thread.sleep(5000);
        }
        kafkaProducer.close();
    }

    private static Properties initProperties(CommandLine commandLine) {
        String bootstrapServers = commandLine.getOptionValue(CommandlineOption.BOOTSTRAP_SERVERS.getShortName(), "localhost:9092");

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    private static CommandLine getCommandLine(String args[]) {
        CommandLineHelper commandLineHelper = new CommandLineHelper();
        Optional<CommandLine> commandLineOptional = commandLineHelper.parseCommandLine(args);

        if (!commandLineOptional.isPresent()) {
            throw new RuntimeException("Could not parse commandline");
        }

        CommandLine commandLine = commandLineOptional.get();

        if (commandLine.hasOption(CommandlineOption.HELP.getShortName())) {
            commandLineHelper.showHelp();
        }

        return commandLineOptional.get();
    }
}
