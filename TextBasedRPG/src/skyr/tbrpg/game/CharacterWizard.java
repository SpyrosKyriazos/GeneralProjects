/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.game;

import skyr.tbrpg.entities.GameCharacter;
import skyr.tbrpg.enums.ClassName;
import skyr.tbrpg.enums.RaceName;
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
    public void beforeCommand() {
        super.beforeCommand();
        if (name == null) {
            System.out.println("Insert name:");
        } else if (raceName == null) {
            System.out.println("Insert race:");
            outputManager.showInputOptions(RaceName.values());
        } else if (className == null) {
            System.out.println("Insert class:");
            outputManager.showInputOptions(ClassName.values());
        }
    }

    @Override
    public void afterCommand(String command) {
        super.afterCommand(command);
        if (name == null) {
            name = command;
        } else if (raceName == null) {
            raceName = command;
        } else if (className == null) {
            className = command;
        }else{
            EntitiesGenerator entitiesGenerator = new EntitiesGenerator();
            character = entitiesGenerator.generateCharacter(name, raceName, className);
            stop();
        }
    }
}
