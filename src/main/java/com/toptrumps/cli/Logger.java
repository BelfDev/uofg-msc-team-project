package com.toptrumps.cli;

import java.io.IOException;
import java.util.logging.*;

public class Logger {
    public static final String LOGGER = "commandLineLogger";
    public static final String DIVIDER = "-------------------";


    private static Logger instance;

    private static final String FILE_PATH = "./TopTrumps.log";
    java.util.logging.Logger logger;
    private Handler loggerHandler;


    private Logger() {

        logger = java.util.logging.Logger.getLogger(LOGGER);
        this.enable();

    }

    public void enable() {
        try {
            loggerHandler = new FileHandler(FILE_PATH);

            loggerHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return String.format(("%s\n%s\n"), record.getMessage(), DIVIDER);
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.addHandler(loggerHandler);
        logger.setLevel(java.util.logging.Level.FINE);
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void logToFileIfEnabled(String logEntry) {
        java.util.logging.Logger.getLogger(LOGGER).fine(logEntry);
    }

}
