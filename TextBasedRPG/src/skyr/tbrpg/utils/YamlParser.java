/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.utils;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import skyr.tbrpg.entities.CharacterClass;
import skyr.tbrpg.entities.Race;

/**
 *
 * @author Spyros
 */
public class YamlParser {

    private Map<Integer, Race> races;
    private Map<Integer, CharacterClass> classes;
    private static YamlParser yamlParser;

    public static YamlParser getYamlParserInstance() {
        if (yamlParser == null) {
            yamlParser = new YamlParser();
        }
        return yamlParser;
    }

    public Map<Integer, Race> getRaces() {
        if (races == null) {
            parseRaces();
        }
        return races;
    }

    public Map<Integer, CharacterClass> getClasses() {
        if (classes == null) {
            parseClasses();
        }
        return classes;
    }

    private void parseRaces() {
        races = new HashMap<Integer, Race>();
        try {
            YamlReader reader = new YamlReader(new FileReader("races.yml"));
            boolean running = true;
            while (running) {
                Race race = reader.read(Race.class);
                if (race == null) {
                    running = false;
                } else {
                    races.put(race.getId(), race);
                }
            }
        } catch (YamlException ex) {
            Logger.getLogger(YamlParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(YamlParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void parseClasses() {
        classes = new HashMap<Integer, CharacterClass>();
        try {
            YamlReader reader = new YamlReader(new FileReader("classes.yml"));
            boolean running = true;
            while (running) {
                CharacterClass clazz = reader.read(CharacterClass.class);
                if (clazz == null) {
                    running = false;
                } else {
                    classes.put(clazz.getId(), clazz);
                }
            }
        } catch (YamlException ex) {
            Logger.getLogger(YamlParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(YamlParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
