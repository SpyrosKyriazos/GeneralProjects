/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Spyros
 */
public class ServerInputThread implements Runnable {

    private boolean running = true;
    private Socket socket;
    private int id;
    private ServerGameThread gameThread;

    public ServerInputThread(Socket socket, int id, ServerGameThread gameThread) {
        this.socket = socket;
        this.id = id;
        this.gameThread = gameThread;
    }

    @Override
    public void run() {
        DataInputStream inputStream = null;
        try {
            inputStream = new DataInputStream(socket.getInputStream());

            loop(inputStream);

        } catch (IOException ex) {
            Logger.getLogger(ServerInputThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerInputThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void loop(DataInputStream inputStream) throws IOException {
        while (running) {
            String command = inputStream.readUTF();
            gameThread.addInput(id, command);
        }
    }

    public void stop() {
        running = false;
    }
}
