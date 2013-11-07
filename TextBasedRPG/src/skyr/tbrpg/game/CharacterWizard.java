/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.game;

import skyr.tbrpg.entities.GameCharacter;
import skyr.tbrpg.exceptions.UnrecognisedCommandException;
import skyr.tbrpg.utils.EntitiesGenerator;
import skyr.tbrpg.utils.OutputManager;

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

    public CharacterWizard() {
        outputManager = new OutputManager();
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
//            outputManager.showInputOptions(RaceName.values());
        } else if (className == null) {
            System.out.println("Insert class:");
//            outputManager.showInputOptions(ClassName.values());
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
                switch (choice) {
                    case 1:
                        raceName = "human";
                        break;
                    case 2:
                        raceName = "elf";
                        break;
                    case 3:
                        raceName = "dwarf";
                        break;
                    case 4:
                        raceName = "goblin";
                        break;
                    default:
                        throw new UnrecognisedCommandException("");
                }
            } catch (NumberFormatException numberFormatException) {
            } catch (UnrecognisedCommandException unrecognisedCommandException) {
            }
        } else if (className == null) {
            try {
                Integer choice = Integer.parseInt(command);
                switch (choice) {
                    case 1:
                        className = "warrior";
                        break;
                    case 2:
                        className = "mage";
                        break;
                    default:
                        throw new UnrecognisedCommandException("");
                }
            } catch (NumberFormatException numberFormatException) {
            } catch (UnrecognisedCommandException unrecognisedCommandException) {
            }
            EntitiesGenerator entitiesGenerator = new EntitiesGenerator();
            character = entitiesGenerator.generateCharacter(name, raceName, className);
            stop();
        }
    }
}
