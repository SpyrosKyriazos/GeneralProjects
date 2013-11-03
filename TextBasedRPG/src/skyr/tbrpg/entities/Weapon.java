/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
