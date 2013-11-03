/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.game.states;

import java.security.KeyStore;
import java.util.Map;
import skyr.tbrpg.entities.GameCharacter;
import skyr.tbrpg.entities.Item;
import skyr.tbrpg.entities.Room;
import skyr.tbrpg.enums.Attribute;
import skyr.tbrpg.enums.Command;
import skyr.tbrpg.exceptions.UnrecognisedCommandException;
import skyr.tbrpg.game.GameBase;
import skyr.tbrpg.utils.EntitiesGenerator;

/**
 *
 * @author Spyros
 */
public class AdventureState extends AbstractGameState {

    private EntitiesGenerator entitiesGenerator;
    private GameCharacter character1;
    private GameCharacter character2;
    private GameCharacter character3;
    private GameCharacter character4;
    private Room currentRoom;

    public AdventureState(GameBase gameBase) {
        super(gameBase);
        entitiesGenerator = new EntitiesGenerator();
    }

    @Override
    public void init() {
        //TODO if saved
        if (character1 == null) {
            System.out.println("Please create character");
            System.out.println("create_char character_name race_name");
        }
    }

    @Override
    public void update(String command) {
        checkCommand(command);
    }

    @Override
    public void chooseAction(Command command, String[] params) throws UnrecognisedCommandException {
        if (character1 == null) {
            if (!command.equals(Command.CREATE_CHAR)) {
                System.out.println("Please create character");
                System.out.println("create_char character_name race_name");
                throw new UnrecognisedCommandException(null);
            }
        } else if (currentRoom == null) {
            currentRoom = entitiesGenerator.generateRoom(1, new int[]{character1.getLevel()});
            roomInfo();
        }
        switch (command) {
            case EXIT:
                gameBase.removeGameState(this);
                gameBase.addGameState(new MenuState(gameBase));
                break;
            case CREATE_CHAR:
                if (params.length < 2) {
                    throw new UnrecognisedCommandException("parameters missing");
                }
                initializeCharacters(params[0], params[1]);
                showInventory();
                break;
            case INVENTORY:
                showInventory();
                break;
            case I:
                showInventory();
                break;
            default:
                throw new UnrecognisedCommandException(command.toString());
        }
    }

    private void initializeCharacters(String charName, String raceName) {
        //if not saved new char
        character1 = entitiesGenerator.generateCharacter(charName, raceName);
    }

    private void showInventory() {
        System.out.println("Your character info:");
        for (Map.Entry<Attribute, Integer> entry : character1.getAttributes().entrySet()) {
            Attribute attribute = entry.getKey();
            Integer integer = entry.getValue();
            System.out.println(attribute.toString() + ": " + integer);
        }
        System.out.println("Your bag contains:");
        for (Item item : character1.getItems()) {
            System.out.println(item.getName());
        }
    }

    private void roomInfo() {
        GameCharacter monster = currentRoom.getMonster();
        if (monster != null) {
            StringBuilder builder = new StringBuilder("You are fighting ");
            builder.append(monster.getName());
            builder.append(", level ");
            builder.append(monster.getLevel());
            builder.append(" and health ");
            builder.append(monster.getAttributes().get(Attribute.HEALTH));
            System.out.println(builder.toString());
        } else {
            currentRoom.getLoot().size();
        }
    }
}
