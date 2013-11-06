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

import skyr.tbrpg.enums.MenuCommand;
import skyr.tbrpg.exceptions.UnrecognisedCommandException;
import skyr.tbrpg.game.GameBase;
import skyr.tbrpg.utils.OutputManager;
import skyr.tbrpg.utils.PropertyManager;

/**
 *
 * @author Spyros
 */
public class MenuState extends AbstractGameState {

    private PropertyManager propertyManager;
    private OutputManager outputManager;
    private boolean requestingParams = false;

    public MenuState(GameBase gameBase) {
        super(gameBase);
        propertyManager = new PropertyManager(PropertyManager.CAPTIONS_PATH);
        outputManager = new OutputManager();
    }

    @Override
    public void init() {
        System.out.println(propertyManager.getProperty("intro"));
    }

    @Override
    public void beforeCommand() {
        if (requestingParams) {
            System.out.println("Insert parameters:");
        } else {
            System.out.println("Select action:");
            outputManager.showInputOptions(MenuCommand.values());
        }
    }

    @Override
    public void afterCommand(String command) {
        checkCommand(command, requestingParams);
    }

    @Override
    public void chooseAction(Integer command, String[] params) throws UnrecognisedCommandException {
        if (command != null) {
            switch (command.intValue()) {
                case 3://exit
                    gameBase.stop();
                    System.out.println("Goodbye!!!");
                    break;
                case 1://host
                    if (params != null && params.length > 0) {
                        host(params);
                        requestingParams = false;
                    } else {
                        System.out.println("Please enter world name");
                        requestingParams = true;
                    }
                    break;
                case 2://join
                    System.out.println("work in progress");
                    break;
                default:
                    throw new UnrecognisedCommandException(command.toString());
            }
        } else {
            //handle parameters
        }
    }

    private void host(String[] params) {
        AdventureState adventureState = new AdventureState(gameBase);
        gameBase.setGameState(adventureState);
    }
}
