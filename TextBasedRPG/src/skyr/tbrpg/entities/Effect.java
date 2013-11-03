/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
