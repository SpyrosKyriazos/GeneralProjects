/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;
import skyr.tbrpg.entities.GameCharacter;
import skyr.tbrpg.entities.Item;
import skyr.tbrpg.entities.Race;
import skyr.tbrpg.entities.Room;
import skyr.tbrpg.entities.Weapon;
import skyr.tbrpg.enums.Attribute;
import skyr.tbrpg.enums.MonsterName;
import skyr.tbrpg.enums.RaceName;
import skyr.tbrpg.enums.WeaponName;

/**
 *
 * @author Spyros
 */
public class EntitiesGenerator {

    public GameCharacter generateCharacter(String charName, String raceName) {

        GameCharacter character = new GameCharacter(charName, getRaceFromString(raceName));
        //attributes
        Map<Attribute, Integer> atrs = new EnumMap<Attribute, Integer>(Attribute.class);
        for (Attribute atr : Attribute.values()) {
            atrs.put(atr, 1);
        }
        character.setAttributes(atrs);
        //lvl
        character.setLevel(1);
        character.setXp(0);
        //equipment
        character.setItems(new ArrayList<Item>());
        Weapon weapon = generateFirstWeapon();
        character.addItem(weapon);
        character.setEquippedWeapon(weapon);
        return character;
    }

    public Room generateRoom(int playersNum, int[] levels) {
        Random randomGenerator = new Random();
        double monsterChance = randomGenerator.nextDouble();
        double lootChance = randomGenerator.nextDouble();
        GameCharacter monster = null;
        Collection<Item> loot = new ArrayList<Item>();
        if (monsterChance > 0.3) {
            monster = generateMonster(playersNum, levels);
        }
        if (lootChance > 0.6) {
            loot.add(generateLootWeapon(levels[0]));
        }
        if (lootChance > 0.8) {
            loot.add(generateLootWeapon(levels[0]));
        }
        Room room = new Room(monster, loot);
        return room;
    }

    private GameCharacter generateMonster(int playersNum, int[] levels) {
        MonsterName[] names = MonsterName.values();
        Random randomGenerator = new Random();
        String name = names[randomGenerator.nextInt(names.length)].toString();
        GameCharacter character = new GameCharacter(name, null);
        //attributes
        Map<Attribute, Integer> atrs = new EnumMap<Attribute, Integer>(Attribute.class);
        for (Attribute atr : Attribute.values()) {
            atrs.put(atr, levels[0]);
        }
        character.setAttributes(atrs);
        //lvl
        character.setLevel(levels[0]);
        character.setXp(0);
        //equipment
//        character.setItems(new ArrayList<Item>());
//        Weapon weapon = generateFirstWeapon();
//        character.addItem(weapon);
//        character.setEquippedWeapon(weapon);
        return character;
    }

    private Race getRaceFromString(String raceNameString) {
        RaceName raceName = RaceName.valueOf(raceNameString.toUpperCase());
        switch (raceName) {
            case HUMAN:
                return new Race(raceName, null);
            default:
                return null;
        }
    }

    private Weapon generateFirstWeapon() {
        Weapon weapon = new Weapon(WeaponName.DAGGER.toString(), 1, null, null);
        return weapon;
    }

    private Weapon generateLootWeapon(int playerLevel) {
        WeaponName[] names = WeaponName.values();
        Random randomGenerator = new Random();
        String name = names[randomGenerator.nextInt(names.length)].toString();
        Weapon weapon = new Weapon(name, playerLevel, null, null);
        return weapon;
    }
}
