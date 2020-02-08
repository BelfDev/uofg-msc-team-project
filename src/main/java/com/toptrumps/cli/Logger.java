package com.toptrumps.cli;

import java.io.IOException;
import java.util.logging.*;

public class Logger {
    public static final String LOGGER = "commandLineLogger";
    public static final String DIVIDER = "-------------------";

    private final String filePath;
    java.util.logging.Logger logger;
    private Handler loggerHandler;



    public Logger(String filePath){
        logger = java.util.logging.Logger.getLogger(LOGGER);

        this.filePath = filePath;
    }

    public void enable(){
        try {
            loggerHandler = new FileHandler(filePath);

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
        logger.setLevel(java.util.logging.Level.INFO);
    }

    public static void logToFile(String logEntry){
        java.util.logging.Logger.getLogger(LOGGER).info(logEntry);
    }

}