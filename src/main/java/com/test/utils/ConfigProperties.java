package com.test.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class ConfigProperties {

    private static Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream("src/main/resources/config.properties"),
                    "UTF-8"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
