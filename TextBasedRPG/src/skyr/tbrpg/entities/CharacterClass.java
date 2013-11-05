/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.entities;

import java.util.Collection;
import skyr.tbrpg.enums.ClassName;

/**
 *
 * @author User
 */
public class CharacterClass {
    private ClassName className;
    private Collection<Effect> effects;

    public CharacterClass(ClassName className, Collection<Effect> effects) {
        this.className = className;
        this.effects = effects;
    }

    public ClassName getClassName() {
        return className;
    }

    public void setClassName(ClassName className) {
        this.className = className;
    }

    public Collection<Effect> getEffects() {
        return effects;
    }

    public void setEffects(Collection<Effect> effects) {
        this.effects = effects;
    }
    
}
