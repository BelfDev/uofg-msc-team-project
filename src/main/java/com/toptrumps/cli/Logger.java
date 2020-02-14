package com.toptrumps.cli;

import java.io.IOException;
import java.util.logging.*;

import static java.util.logging.Level.FINE;

/**
 *  This class contains the Logger functionality for the logging of the debug file to
 *  TomTrumps.log in the root directory. The log is only produced if the user requests it.
 *  The class is built in the singleton design pattern.
 */

public class Logger {

    public static final String LOGGER = "commandLineLogger";
    public static final String DIVIDER = "-------------------";

    private static final String FILE_PATH = "./TopTrumps.log";

    private static Logger instance;
    java.util.logging.Logger logger;

    private Handler loggerHandler;
    private boolean isEnabled;


    private Logger() {
        logger = java.util.logging.Logger.getLogger(LOGGER); //Creates a Logger with the name set to the string in LOGGER
        this.isEnabled = false;
    }

    /**
     * Returns whether the LoggerHandler has been initialized and
     * added to the Logger.
     *
     * @return a boolean value indicating if the logger is enabled
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * Creates a FileHandler to write to a log to a specified file (path held in FILE_PATH).
     * Also creates a handler to format the message before logging it, and
     * adds it to the Logger. Sets the logger's output level to FINE.
     */
    public void enable() {
        try {
            loggerHandler = new FileHandler(FILE_PATH);
            loggerHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return String.format(("%s\n%s\n"), record.getMessage(), DIVIDER);
                }
            });

            isEnabled = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.addHandler(loggerHandler);
        logger.setLevel(FINE);
    }


    /**
     * The Logger class follows the singleton design pattern:
     * there can only be a single instance of the Logger. Therefore, the method
     * checks whether there is an existing instance of Logger, before returning it if it exists;
     * if it doesn't the method creates a new Logger instance and returns it.
     * @return Logger
     */
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    /**
     * Wrapper that verifies if the Logger is enabled, and if true, writes
     * messages passed to it to the log file.
     *
     * @param logEntry
     */
    public void logToFileIfEnabled(String logEntry) {
        logToFile(logEntry);
    }

    /**
     * Writes messages passed to it to the log.
     *
     * @param logEntry
     */
    public void logToFile(String logEntry) {
        java.util.logging.Logger.getLogger(LOGGER).fine(logEntry);
    }

}
