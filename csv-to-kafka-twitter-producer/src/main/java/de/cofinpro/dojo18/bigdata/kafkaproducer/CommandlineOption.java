package de.cofinpro.dojo18.bigdata.kafkaproducer;

import org.apache.commons.cli.Options;

/**
 * Created by David Olah on 21.04.2018.
 */
enum CommandlineOption {
    BOOTSTRAP_SERVERS("b", "bootstrap-servers", true, "addresses of kafka bootstrap servers (multiple values allowed, separated by ','"),
    HELP("h", "help", false, "shows this help text");

    String shortName;
    String longName;
    boolean hasArg;
    String description;

    CommandlineOption(String shortName, String longName, boolean hasArg, String description) {
        this.shortName = shortName;
        this.longName = longName;
        this.hasArg = hasArg;
        this.description = description;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }

    public boolean isHasArg() {
        return hasArg;
    }

    public String getDescription() {
        return description;
    }

    public void addToOption(Options options) {
        options.addOption(
                this.shortName,
                this.longName,
                this.hasArg,
                this.description
        );
    }
}
