/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Spyros
 */
public class PropertyManager {

    public static final String CAPTIONS_PATH = "skyr/tbrpg/properties/captions.properties";
    private Properties prop;

    public PropertyManager(String path) {
        try {
            prop = new Properties();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = loader.getResourceAsStream(path);
            prop.load(stream);
        } catch (IOException ex) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getProperty(String name) {
        return prop.getProperty(name);
    }
}
