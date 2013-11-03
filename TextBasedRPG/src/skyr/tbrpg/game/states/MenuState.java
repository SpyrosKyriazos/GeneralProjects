/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
                for (Command c : Command.values()) {
                    System.out.println(c);
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
