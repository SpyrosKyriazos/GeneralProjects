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

import skyr.tbrpg.enums.Attribute;

/**
 *
 * @author Spyros
 */
public class Effect {

    private int modifier;
    private Attribute attribute;
    private boolean percentage;
    private boolean self;
    private Race vsRace;

    public Effect() {
    }
    
    public Effect(int modifier, Attribute attribute, boolean percentage, boolean self, Race vsRace) {
        this.modifier = modifier;
        this.attribute = attribute;
        this.percentage = percentage;
        this.self = self;
        this.vsRace = vsRace;
    }
    
    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public boolean isPercentage() {
        return percentage;
    }

    public void setPercentage(boolean percentage) {
        this.percentage = percentage;
    }

    public boolean isSelf() {
        return self;
    }

    public void setSelf(boolean self) {
        this.self = self;
    }

    public Race getVsRace() {
        return vsRace;
    }

    public void setVsRace(Race vsRace) {
        this.vsRace = vsRace;
    }
}
