/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.game.states;

import java.util.Arrays;
import skyr.tbrpg.enums.Command;
import skyr.tbrpg.exceptions.UnrecognisedCommandException;
import skyr.tbrpg.game.GameBase;
import skyr.tbrpg.game.GameState;

/**
 *
 * @author Spyros
 */
public abstract class AbstractGameState implements GameState {

    protected GameBase gameBase;

    public AbstractGameState(GameBase gameBase) {
        this.gameBase = gameBase;
    }

    protected void checkCommand(String inputCommand) {
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

            chooseAction(command, Arrays.copyOfRange(commandParts, 1, commandParts.length));

        } catch (UnrecognisedCommandException unrecognisedCommandException) {
            System.out.println("Unrecognised Command: " + unrecognisedCommandException.getMessage());
        }
    }

    public abstract void chooseAction(Command command, String[] params) throws UnrecognisedCommandException;
}
