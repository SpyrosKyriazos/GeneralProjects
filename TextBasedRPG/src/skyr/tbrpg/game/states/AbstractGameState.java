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
package skyr.tbrpg.game.states;

import java.util.Arrays;
import skyr.tbrpg.enums.MenuCommand;
import skyr.tbrpg.exceptions.UnrecognisedCommandException;
import skyr.tbrpg.game.GameBase;
import skyr.tbrpg.game.GameState;

/**
 *
 * @author Spyros
 */
public abstract class AbstractGameState implements GameState {

    protected GameBase gameBase;
    private int previousCommand;

    public AbstractGameState(GameBase gameBase) {
        this.gameBase = gameBase;
    }

    protected void checkCommand(String inputCommand, boolean isParameters) {
        try {
            if (isParameters) {
                String[] commandParts = inputCommand.split("\\s+");
                if (commandParts.length == 0) {
                    throw new UnrecognisedCommandException("Missing parameters");
                }
                chooseAction(previousCommand, commandParts);
            } else {
                Integer choice = Integer.parseInt(inputCommand);
                previousCommand = choice;
                chooseAction(choice, null);
            }

        } catch (NumberFormatException numberFormatException) {
            System.out.println("Unrecognised Command: " + numberFormatException.getMessage());
        } catch (UnrecognisedCommandException unrecognisedCommandException) {
            System.out.println("Unrecognised Command: " + unrecognisedCommandException.getMessage());
        }
    }

    public abstract void chooseAction(Integer command, String[] params) throws UnrecognisedCommandException;
}
