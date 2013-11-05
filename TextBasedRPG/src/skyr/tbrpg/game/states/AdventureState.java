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

import java.util.Map;
import java.util.Random;
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
            if (!command.equals(Command.CREATE_CHARACTER) && !command.equals(Command.CHAR)) {
                System.out.println("Please create character");
                System.out.println("create_character character_name race_name");
                throw new UnrecognisedCommandException(null);
            }
        } else if (currentRoom == null) {
            initializeRoom();
        }
        switch (command) {
            case MENU:
                gameBase.removeGameState(this);
                gameBase.addGameState(new MenuState(gameBase));
                System.out.println("You are back in the menu");
                break;
            case CHAR:
            case CREATE_CHARACTER:
//                if (params.length < 2) {
//                    throw new UnrecognisedCommandException("parameters missing");
//                }
                initializeCharacters(
                        params.length > 0 ? params[0] : null,
                        params.length > 1 ? params[1] : null,
                        params.length > 2 ? params[2] : null);
                break;
            case INVENTORY:
            case I:
                showInventory();
                break;
            case ROOM:
                roomInfo();
                break;
            case ATTACK:
                attack();
                break;
            case LOOT:
                loot();
                break;
            case DOOR:
                nextRoom();
                break;
            case HELP:
                int i = 0;
                for (Command c : Command.values()) {
                    System.out.println((i++) + ". " + c);
                }
                break;
            default:
                throw new UnrecognisedCommandException(command.toString());
        }
    }

    private void initializeCharacters(String charName, String raceName, String className) {
        //if not saved new char
        character1 = entitiesGenerator.generateCharacter(charName, raceName, className);
        showInventory();
        initializeRoom();
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
            roomInfo();
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
        System.out.println(character1.getEquippedWeapon());
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
