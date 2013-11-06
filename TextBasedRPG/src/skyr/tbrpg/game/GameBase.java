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
package skyr.tbrpg.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import skyr.tbrpg.game.states.MenuState;

/**
 *
 * @author Spyros
 */
public class GameBase implements GameState {

    private boolean running;
    private Scanner scanner;
    private Collection<GameState> gameStates;

    public GameBase() {
        scanner = new Scanner(System.in);
        gameStates = new ArrayList<GameState>();
    }

    public Collection<GameState> getGameStates() {
        return gameStates;
    }

    public void setGameState(GameState state) {
        gameStates.clear();
        gameStates.add(state);
    }
    
    public void addGameState(GameState state) {
        gameStates.add(state);
    }
    
    public void removeGameState(GameState state) {
        gameStates.remove(state);
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        running = false;
    }

    public void start() {
        running = true;
        init();
        for (GameState gameState : gameStates) {
            gameState.init();
        }
        while (running) {
            beforeCommand();
            for (GameState gameState : gameStates) {
                gameState.beforeCommand();
            }
            String inputCommand = scanner.nextLine();
            afterCommand(inputCommand);
            for (GameState gameState : gameStates) {
                gameState.afterCommand(inputCommand);
            }
        }
    }

    @Override
    public void init() {
        MenuState menuState = new MenuState(this);
        addGameState(menuState);
    }

    @Override
    public void beforeCommand() {
        
    }
    
    @Override
    public void afterCommand(String command) {
        
    }
}
