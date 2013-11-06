/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.enums;

/**
 *
 * @author User
 */
public enum AdventureCommand implements EnumsInterface{

    ATTACK(1),
    SKILL(2),
    INVENTORY(3),
    ROOM(4),
    LOOT(5),
    DOOR(6),
    EQUIP(7),
    MENU(8);
    private int id;

    private AdventureCommand(int id) {
        this.id = id;
    }

    @Override
    public int id() {
        return id;
    }
    
    @Override
    public String toString() {
        return id + ". " + name();
    }
}
