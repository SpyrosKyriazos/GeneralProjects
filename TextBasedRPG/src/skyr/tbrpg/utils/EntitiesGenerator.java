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
package skyr.tbrpg.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;
import skyr.tbrpg.entities.CharacterClass;
import skyr.tbrpg.entities.Effect;
import skyr.tbrpg.entities.GameCharacter;
import skyr.tbrpg.entities.Item;
import skyr.tbrpg.entities.Race;
import skyr.tbrpg.entities.Room;
import skyr.tbrpg.entities.Weapon;
import skyr.tbrpg.enums.Attribute;
import skyr.tbrpg.enums.ClassName;
import skyr.tbrpg.enums.RaceName;
import skyr.tbrpg.enums.WeaponName;

/**
 *
 * @author Spyros
 */
public class EntitiesGenerator {

    public GameCharacter generateCharacter(String charName, String raceName, String className) {

        GameCharacter character = new GameCharacter(charName, generateRaceObject(raceName));
        //attributes
        Map<Attribute, Integer> atrs = new EnumMap<Attribute, Integer>(Attribute.class);
        for (Attribute atr : Attribute.values()) {
            atrs.put(atr, 1);
        }
        atrs.put(Attribute.HEALTH, 10);
        character.setAttributes(atrs);
        //class
        character.setClazz(generateClassObject(className));
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
        ClassName[] classNames = ClassName.values();
        Random randomGenerator = new Random();
        ClassName className = classNames[randomGenerator.nextInt(classNames.length)];
        String name1 = className.toString();
        RaceName[] raceNames = RaceName.values();
        RaceName raceName = raceNames[randomGenerator.nextInt(raceNames.length)];
        String name2 = raceName.toString();
        GameCharacter character = new GameCharacter(name2 + " " + name1, generateRaceObject(name2));
        //class
        character.setClazz(generateClassObject(name1));
        //attributes
        Map<Attribute, Integer> atrs = new EnumMap<Attribute, Integer>(Attribute.class);
        for (Attribute atr : Attribute.values()) {
            atrs.put(atr, levels[0]);
        }
        atrs.put(Attribute.HEALTH, levels[0] * 2);
        character.setAttributes(atrs);
        //lvl
        character.setLevel(levels[0]);
        character.setXp(0);
        //equipment
        character.addItem(generateLootWeapon(levels[0]));
        return character;
    }

    private Race generateRaceObject(String raceNameString) {
        try {
            RaceName raceName = RaceName.valueOf(raceNameString.toUpperCase());
            switch (raceName) {
                case HUMAN:
                    return new Race(raceName, null);
                default:
                    return null;
            }
        } catch (Exception e) {
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

    private CharacterClass generateClassObject(String classNameString) {
        try {
            ClassName className = ClassName.valueOf(classNameString.toUpperCase());
            Effect effect1 = new Effect();
            switch (className) {
                case WARRIOR:
                    effect1.setAttribute(Attribute.DEFENCE);
                    effect1.setModifier(1);
                    break;
                case MAGE:
                    effect1.setAttribute(Attribute.POWER);
                    effect1.setModifier(1);
                    break;
                default:
                    return null;
            }
            ArrayList<Effect> effects = new ArrayList<Effect>();
            effects.add(effect1);
            CharacterClass characterClass = new CharacterClass(className, effects);
            return characterClass;
        } catch (Exception e) {
            return null;
        }
    }
}
