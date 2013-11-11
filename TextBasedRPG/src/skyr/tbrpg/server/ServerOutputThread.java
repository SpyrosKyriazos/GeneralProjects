/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Spyros
 */
public class ServerOutputThread implements Runnable {

    private boolean running = true;
    private Map<Integer, Socket> sockets;
    private Map<Integer, String> playerOutput;

    public ServerOutputThread() {
        sockets = new HashMap<Integer, Socket>();
        playerOutput = new HashMap<Integer, String>();
    }

    public void addSocket(Integer id, Socket s) {
        sockets.put(id, s);
    }

    public void addOutput(Integer id, String command) {
        playerOutput.put(id, command);
    }

    @Override
    public void run() {
        while (running) {
            if (!playerOutput.isEmpty()) {
                for (Map.Entry<Integer, String> entry : playerOutput.entrySet()) {
                    Integer id = entry.getKey();
                    String message = entry.getValue();
                    if (id.intValue() == 0) {
                        for (Socket socket : sockets.values()) {
                            try {
                                new DataOutputStream(socket.getOutputStream()).writeUTF(message);
                            } catch (IOException ex) {
                                Logger.getLogger(ServerOutputThread.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } else {
                        try {
                            new DataOutputStream(sockets.get(id).getOutputStream()).writeUTF(message);
                        } catch (IOException ex) {
                            Logger.getLogger(ServerOutputThread.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }

    public void stop() {
        running = false;
    }
}
