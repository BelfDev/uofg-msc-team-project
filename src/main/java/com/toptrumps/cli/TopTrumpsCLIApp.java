package com.toptrumps.cli;


import ch.qos.logback.core.util.FileUtil;

import java.io.File;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApp {

    private static final String LOG_PATH = "./";
    private static final String LOG_FILENAME = "TopTrumps.log";


    /**
     * This main method is called by TopTrumps.java when the user specifies that they want to run in
     * command line mode. The contents of args[0] is whether we should write game logs to a file.
     */
    public static void main(String[] args) {





        boolean writeGameLogsToFile = false; // Should we write game logs to file?
         if (args[0].equalsIgnoreCase("true")) writeGameLogsToFile = true; // Command line selection


        if(writeGameLogsToFile){
            Logger logger = new Logger(LOG_PATH + LOG_FILENAME);
            logger.enable();
        }
        Logger.logToFile("MAD STAX FTW.");
        // TODO remove this.

        CommandLineController commandLineUI = new CommandLineController();
        commandLineUI.start();

    }

}
