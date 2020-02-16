package com.toptrumps.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toptrumps.online.configuration.SimpleTopTrumpsConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static com.toptrumps.TopTrumps.CONFIG_FILE_NAME;

public class ResourceLoader {

    private ResourceLoader() {
    }

    public static InputStream getResource(String fileName) {
        ClassLoader classLoader = ResourceLoader.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("File not found");
        } else {
            return classLoader.getResourceAsStream(fileName);
        }
    }

    public static String getDeckFileName() {
        // Creates a jackson ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        // Loads configuration file from the root directory
        File file = new File(CONFIG_FILE_NAME);
        try {
            // Returns the parsed deckFile name from the JSON configuration file
            return objectMapper.readValue(file, SimpleTopTrumpsConfig.class).getDeckFileName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
