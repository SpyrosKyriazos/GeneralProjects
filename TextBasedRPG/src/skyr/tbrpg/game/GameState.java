/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.game;

/**
 *
 * @author Spyros
 */
public interface GameState {

    public void init();

    public void update(String command);
}
