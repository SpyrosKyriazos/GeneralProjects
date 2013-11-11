/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.server;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Spyros
 */
public class ServerGameThread implements Runnable {

    private boolean running = true;
    private Map<Integer, String> playersInput;

    public ServerGameThread() {
        playersInput = new HashMap<Integer, String>();
    }

    public void addInput(Integer player, String command) {
        playersInput.put(player, command);
    }

    public Map<Integer, String> getPlayersInput() {
        return playersInput;
    }

    @Override
    public void run() {
        while (running) {
        }
    }

    public void stop() {
        running = false;
    }
}
