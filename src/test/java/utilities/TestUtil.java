package utilities;

import java.io.IOException;
import java.util.Properties;

public class TestUtil {
    private final Properties properties;

    public TestUtil() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("test-data.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Long getId() {
        return Long.parseLong(properties.getProperty("id"));
    }

    public Long getInvalidID() {
        return Long.parseLong(properties.getProperty("invalid_id"));
    }

    public String getPhotoURL() {
        return properties.getProperty("photoURL");
    }
}