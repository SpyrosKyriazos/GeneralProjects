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

import skyr.tbrpg.enums.Command;
import skyr.tbrpg.exceptions.UnrecognisedCommandException;
import skyr.tbrpg.game.GameBase;
import skyr.tbrpg.utils.PropertyManager;

/**
 *
 * @author Spyros
 */
public class MenuState extends AbstractGameState {

    private PropertyManager propertyManager;

    public MenuState(GameBase gameBase) {
        super(gameBase);
        propertyManager = new PropertyManager(PropertyManager.CAPTIONS_PATH);
    }

    @Override
    public void init() {
        System.out.println(propertyManager.getProperty("intro"));
    }

    @Override
    public void update(String command) {
        checkCommand(command);
    }

    @Override
    public void chooseAction(Command command, String[] params) throws UnrecognisedCommandException {
        switch (command) {
            case EXIT:
                gameBase.stop();
                System.out.println("Goodbye!!!");
                break;
            case HOST:
                if (params.length < 2) {
                    throw new UnrecognisedCommandException("parameters missing");
                }
                host(params);
                break;
            case JOIN:
                System.out.println("work in progress");
                break;
            case HELP:
                int i = 0;
                for (Command c : Command.values()) {
                    System.out.println((i++) + ". " + c);
                }
                break;
            default:
                throw new UnrecognisedCommandException(command.toString());
        }
    }

    private void host(String[] params) {
        gameBase.removeGameState(this);
        gameBase.addGameState(new AdventureState(gameBase));
    }
}
