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

/**
 *
 * @author Spyros
 */
public class Weapon implements Item {

    private String name;
    private Integer level;
    private Effect prefixEffect;
    private Effect suffixEffect;

    public Weapon() {
    }
    
    public Weapon(String name, Integer level, Effect prefixEffect, Effect suffixEffect) {
        this.name = name;
        this.level = level;
        this.prefixEffect = prefixEffect;
        this.suffixEffect = suffixEffect;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Effect getPrefixEffect() {
        return prefixEffect;
    }

    public void setPrefixEffect(Effect prefixEffect) {
        this.prefixEffect = prefixEffect;
    }

    public Effect getSuffixEffect() {
        return suffixEffect;
    }

    public void setSuffixEffect(Effect suffixEffect) {
        this.suffixEffect = suffixEffect;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
    
}
