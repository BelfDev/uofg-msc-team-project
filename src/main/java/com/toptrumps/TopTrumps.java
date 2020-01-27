package com.toptrumps;

import com.toptrumps.cli.TopTrumpsCLIApp;
import com.toptrumps.online.TopTrumpsOnlineApp;

public class TopTrumps {

    /**
     * This is the main class for the TopTrumps Applications
     */
    public static void main(String[] args) {

        System.out.println("--------------------");
        System.out.println("--- Top Trumps   ---");
        System.out.println("--------------------");

        // command line switches
        boolean onlineMode = false;
        boolean commandLineMode = false;
        boolean printTestLog = false;

        // check the command line for what switches are active
        for (String arg : args) {

            if (arg.equalsIgnoreCase("-t")) printTestLog = true;
            if (arg.equalsIgnoreCase("-c")) commandLineMode = true;
            if (arg.equalsIgnoreCase("-o")) onlineMode = true;

        }

        // We cannot run online and command line mode simultaneously // Do we want to create an exception for this?
        if (onlineMode && commandLineMode) {
            throw new IllegalArgumentException("ERROR: Both online and command line mode selected, select one or the other!");
        }

        // Start the appropriate application
        if (onlineMode) {
            // Start the online application
            String[] commandArgs = {"server", "TopTrumps.json"};
            TopTrumpsOnlineApp.main(commandArgs);
        } else if (commandLineMode) {
            // Start the command line application
            String[] commandArgs = {String.valueOf(printTestLog)};
            TopTrumpsCLIApp.main(commandArgs);
        }

    }

}
