package webboilerplate.webboilerplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private static PropertiesLoader propertiesLoader = null;
    private static Properties prop = null;

    private PropertiesLoader(){};

    public static PropertiesLoader getPropertiesLoaderInstance(){
        if (propertiesLoader==null){
            propertiesLoader = new PropertiesLoader();
            prop = new Properties();
        }
        return propertiesLoader;
    }


    public Properties loader() {
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
