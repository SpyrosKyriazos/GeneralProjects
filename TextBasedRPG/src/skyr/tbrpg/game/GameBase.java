/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import skyr.tbrpg.enums.Command;
import static skyr.tbrpg.enums.Command.*;
import skyr.tbrpg.exceptions.UnrecognisedCommandException;

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
            System.out.println("Your action:");
            String inputCommand = scanner.nextLine();
            update(inputCommand);
            for (GameState gameState : gameStates) {
                gameState.update(inputCommand);
            }
        }
    }

    @Override
    public void init() {
        System.out.println("place intro here \n\n\n");
    }

    @Override
    public void update(String command) {
        
    }

    private void checkCommand(String inputCommand) {
        try {
            String[] commandParts = inputCommand.split("\\s+");
            if (commandParts.length == 0) {
                throw new UnrecognisedCommandException("No input");
            }
            Command command = null;
            try {
                command = Command.valueOf(commandParts[0].toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new UnrecognisedCommandException(commandParts[0]);
            }
            switch (command) {
                case EXIT:
                    running = false;
                    System.out.println("Goodbye!!!");
                    break;
                default:
                    throw new UnrecognisedCommandException(command.toString());
            }
        } catch (UnrecognisedCommandException unrecognisedCommandException) {
            System.out.println("Unrecognised Command: " + unrecognisedCommandException.getMessage());
        }
    }
}
