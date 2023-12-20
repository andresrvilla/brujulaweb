package net.brujulaweb.repository.config;

import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final Properties appProps;

    static {
        appProps = new Properties();
        try {
            appProps.load(Config.class.getClassLoader().getResourceAsStream("app.properties"));
            appProps.load(Config.class.getClassLoader().getResourceAsStream(appProps.getProperty("env")+"/main.properties"));
            appProps.load(Config.class.getClassLoader().getResourceAsStream(appProps.getProperty("env")+"/db.properties"));
            appProps.load(Config.class.getClassLoader().getResourceAsStream(appProps.getProperty("env")+"/server.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String propertyName) {
        return appProps.getProperty(propertyName);
    }

    public static int getInt(String propertyName) {
        return Integer.parseInt(appProps.getProperty(propertyName));
    }
}
