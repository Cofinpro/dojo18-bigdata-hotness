package de.cofinpro.dojo18.bigdata.kafkaproducer;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Created by David Olah on 21.04.2018.
 */
public class CommandLineHelper {
    private static Logger logger = LoggerFactory.getLogger(CommandLineHelper.class);

    private Options options = new Options();

    public Optional<CommandLine> parseCommandLine(String[] args) {
        CommandlineOption.BOOTSTRAP_SERVERS.addToOption(options);
        CommandlineOption.HELP.addToOption(options);

        CommandLineParser commandLineParser = new DefaultParser();
        Optional<CommandLine> commandLine = Optional.empty();
        try {
            commandLine = Optional.of(commandLineParser.parse(options, args));
        } catch (ParseException e) {
            logger.error("Error during commandline parsing", e);
        }

        return commandLine;
    }

    public void showHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("<this application>", options);
    }
}
