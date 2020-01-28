package com.toptrumps.cli;

import com.toptrumps.core.DeckParser;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApp {

    /**
     * This main method is called by TopTrumps.java when the user specifies that they want to run in
     * command line mode. The contents of args[0] is whether we should write game logs to a file.
     */
    public static void main(String[] args) {

        boolean writeGameLogsToFile = false; // Should we write game logs to file?
        if (args[0].equalsIgnoreCase("true")) writeGameLogsToFile = true; // Command line selection


        CommandLineUI commandLineUI = new CommandLineUI();
        DeckParser d = new DeckParser();


    }

}
