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
