/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.entities;

import java.util.Collection;
import java.util.Map;
import skyr.tbrpg.enums.Attribute;

/**
 *
 * @author Spyros
 */
public class GameCharacter {

    private String name;
    private int level;
    private int xp;
    private Race race;
    private Weapon equippedWeapon;
    private Collection<Item> items;
    private Map<Attribute, Integer> attributes;

    public GameCharacter(String name, Race race) {
        this.name = name;
        this.race = race;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public void setEquippedWeapon(Weapon equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }
    
    public void addItem(Item item){
        items.add(item);
    }

    public Map<Attribute, Integer> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<Attribute, Integer> attributes) {
        this.attributes = attributes;
    }
}
