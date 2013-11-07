/*
 *  Copyright (C) 2013 Spyros Kyriazos
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package skyr.tbrpg.game.states;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import skyr.tbrpg.entities.GameCharacter;
import skyr.tbrpg.entities.Item;
import skyr.tbrpg.entities.Room;
import skyr.tbrpg.enums.AdventureCommand;
import skyr.tbrpg.enums.Attribute;
import skyr.tbrpg.exceptions.UnrecognisedCommandException;
import skyr.tbrpg.game.CharacterWizard;
import skyr.tbrpg.game.GameBase;
import skyr.tbrpg.utils.EntitiesGenerator;
import skyr.tbrpg.utils.OutputManager;

/**
 *
 * @author Spyros
 */
public class AdventureState extends AbstractGameState {

    private EntitiesGenerator entitiesGenerator;
    private OutputManager outputManager;
    private GameCharacter character1;
    private GameCharacter character2;
    private GameCharacter character3;
    private GameCharacter character4;
    private Room currentRoom;

    public AdventureState(GameBase gameBase) {
        super(gameBase);
        entitiesGenerator = new EntitiesGenerator();
        outputManager = new OutputManager();
    }

    @Override
    public void init() {
        if (character1 == null) {
            System.out.println("Please create character");
            CharacterWizard characterWizard = new CharacterWizard();
            gameBase.getGameStates().clear();
            character1 = characterWizard.createCharacter();
            gameBase.addGameState(this);
            initializeRoom();
        }
    }

    @Override
    public void beforeCommand() {
        System.out.println("Select action:");
        Collection<AdventureCommand> commands = new ArrayList<AdventureCommand>();
        commands.addAll(Arrays.asList(AdventureCommand.values()));
        if (currentRoom.getMonster() == null) {
            commands.remove(AdventureCommand.ATTACK);
            commands.remove(AdventureCommand.SKILL);
        } else {
            commands.add(AdventureCommand.LOOT);
        }
        outputManager.showInputOptions(commands.toArray(new Enum[commands.size()]));
    }

    @Override
    public void afterCommand(String command) {
        checkCommand(command, false);
    }

    @Override
    public void chooseAction(Integer command, String[] params) throws UnrecognisedCommandException {
//        if (character1 == null) {
//            if (!command.equals(MenuCommand.CREATE_CHARACTER) && !command.equals(MenuCommand.CHAR)) {
//                System.out.println("Please create character");
//                System.out.println("create_character character_name race_name");
//                throw new UnrecognisedCommandException(null);
//            }
//        } else if (currentRoom == null) {
//            initializeRoom();
//        }
        switch (command) {
            case 8:
                gameBase.removeGameState(this);
                gameBase.addGameState(new MenuState(gameBase));
                System.out.println("You are back in the menu");
                break;
            case 3:
                showInventory();
                break;
            case 4:
                roomInfo();
                break;
            case 1:
                attack();
                break;
            case 5:
                loot();
                break;
            case 6:
                nextRoom();
                break;
            default:
                throw new UnrecognisedCommandException(command.toString());
        }
    }

    private void initializeRoom() {
        currentRoom = entitiesGenerator.generateRoom(1, new int[]{character1.getLevel()});
        roomInfo();
    }

    private void attack() {
        int playerATK = character1.getAttributes().get(Attribute.ATTACK) + character1.getEquippedWeapon().getLevel();
        int monsterDEF = currentRoom.getMonster().getAttributes().get(Attribute.DEFENCE);
        int monsterHealth = currentRoom.getMonster().getAttributes().get(Attribute.HEALTH);
        int damage = playerATK - monsterDEF;
        Random random = new Random();
        int randomnessMod = 4;
        int roll = random.nextInt(randomnessMod) - ((randomnessMod / 3) * 2);
        if (damage + roll > 0) {
            System.out.println("You attack for " + damage + " damage");
            currentRoom.getMonster().getAttributes().put(Attribute.HEALTH, monsterHealth - damage);
            checkForKill();
            roomInfo();
        } else {
            System.out.println("You miss");
        }
    }

    private void loot() {
        if (currentRoom.getLoot().size() > 0) {
            for (Item item : currentRoom.getLoot()) {
                System.out.println("You get 1 " + item.getName());
                character1.addItem(item);
            }
            currentRoom.getLoot().clear();
        } else {
            System.out.println("no loot");
        }
    }

    private void nextRoom() {
        System.out.println("next room");
        initializeRoom();
    }

    private void checkForKill() {
        if (currentRoom.getMonster().getAttributes().get(Attribute.HEALTH) <= 0) {
            for (Item item : currentRoom.getMonster().getItems()) {
                currentRoom.getLoot().add(item);
            }
            currentRoom.setMonster(null);
            System.out.println("monster killed");
        }
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
        System.out.println("You hold:");
        System.out.println(character1.getEquippedWeapon().getName());
    }

    private void roomInfo() {
        if (currentRoom == null) {
            System.out.println("go to next room");
            return;
        }
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
            System.out.println("chests number: " + currentRoom.getLoot().size());
        }
    }
}
