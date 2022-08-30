package webboilerplate.webboilerplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

public class PropertiesLoader {

    private static final Properties prop = new Properties();

    public static Properties loader() {
        /**
         * To get the property value use .get("propertyname")
         */
        try ( InputStream input = new FileInputStream("src/main/resources/config.properties") ) {
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            return prop;
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
