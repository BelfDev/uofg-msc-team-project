package com.toptrumps.core.utils;

import java.io.InputStream;
import java.net.URL;

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

}
