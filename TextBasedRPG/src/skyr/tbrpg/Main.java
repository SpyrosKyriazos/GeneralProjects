/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg;

import skyr.tbrpg.game.GameBase;
import skyr.tbrpg.game.states.MenuState;

/**
 *
 * @author Spyros
 */
public class Main {

    public static void main(String[] args) {
        GameBase gameBase = new GameBase();
        MenuState menuState = new MenuState(gameBase);
        gameBase.addGameState(menuState);
        gameBase.start();
    }
}
