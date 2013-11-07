/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.entities;

import java.util.Collection;

/**
 *
 * @author User
 */
public class CharacterClass {

    private int id;
    private String className;
    private Collection<Effect> effects;

    public CharacterClass() {
    }

    public CharacterClass(int id, String className, Collection<Effect> effects) {
        this.id = id;
        this.className = className;
        this.effects = effects;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String name) {
        this.className = name;
    }

    public Collection<Effect> getEffects() {
        return effects;
    }

    public void setEffects(Collection<Effect> effects) {
        this.effects = effects;
    }
}
