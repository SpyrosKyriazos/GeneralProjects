/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.game;

import skyr.tbrpg.entities.GameCharacter;
import skyr.tbrpg.exceptions.UnrecognisedCommandException;
import skyr.tbrpg.utils.EntitiesGenerator;
import skyr.tbrpg.utils.OutputManager;
import skyr.tbrpg.utils.YamlParser;

/**
 *
 * @author User
 */
public class CharacterWizard extends GameBase {

    private String name;
    private String raceName;
    private String className;
    private OutputManager outputManager;
    private GameCharacter character;
    private YamlParser yamlParser;

    public CharacterWizard() {
        outputManager = new OutputManager();
        yamlParser = YamlParser.getYamlParserInstance();
    }

    public GameCharacter createCharacter() {
        start();
        return character;
    }

    @Override
    public void init() {
    }

    @Override
    public void beforeCommand() {
        super.beforeCommand();
        if (name == null) {
            System.out.println("Insert name:");
        } else if (raceName == null) {
            System.out.println("Insert race:");
            outputManager.showInputOptions(yamlParser.getRaces().values());
        } else if (className == null) {
            System.out.println("Insert class:");
            outputManager.showInputOptions(yamlParser.getClasses().values());
        }
    }

    @Override
    public void afterCommand(String command) {
        super.afterCommand(command);
        if (name == null) {
            name = command;
        } else if (raceName == null) {
            try {
                Integer choice = Integer.parseInt(command);
                raceName = yamlParser.getRaces().get(choice).getRaceName();
            } catch (NumberFormatException numberFormatException) {
            }
        } else if (className == null) {
            try {
                Integer choice = Integer.parseInt(command);
                className = yamlParser.getClasses().get(choice).getClassName();
            } catch (NumberFormatException numberFormatException) {
            }
            EntitiesGenerator entitiesGenerator = new EntitiesGenerator();
            character = entitiesGenerator.generateCharacter(name, raceName, className);
            stop();
        }
    }
}
