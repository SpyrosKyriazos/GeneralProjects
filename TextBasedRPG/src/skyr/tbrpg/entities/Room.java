/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.entities;

import java.util.Collection;

/**
 *
 * @author Spyros
 */
public class Room {

    private GameCharacter monster;
    private Collection<Item> loot;

    public Room(GameCharacter monster, Collection<Item> loot) {
        this.monster = monster;
        this.loot = loot;
    }

    public GameCharacter getMonster() {
        return monster;
    }

    public void setMonster(GameCharacter monster) {
        this.monster = monster;
    }

    public Collection<Item> getLoot() {
        return loot;
    }

    public void setLoot(Collection<Item> loot) {
        this.loot = loot;
    }
}
