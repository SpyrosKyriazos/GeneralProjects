/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.entities;

import skyr.tbrpg.enums.RaceName;

/**
 *
 * @author Spyros
 */
public class Race {
    private RaceName raceName;
    private Effect effect;

    public Race(RaceName raceName, Effect effect) {
        this.raceName = raceName;
        this.effect = effect;
    }
    
    public RaceName getRaceName() {
        return raceName;
    }

    public void setRaceName(RaceName raceName) {
        this.raceName = raceName;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }
    
}
