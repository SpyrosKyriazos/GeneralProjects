/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class ClientInputThread implements Runnable {

    private boolean running = true;
    private Client client;
    private Socket socket;

    public ClientInputThread(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        DataInputStream inputStream = null;
        try {
            inputStream = new DataInputStream(socket.getInputStream());

            loop(inputStream);

        } catch (IOException ex) {
            Logger.getLogger(ClientInputThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientInputThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void loop(DataInputStream inputStream) throws IOException {
        while (running) {
            String command = inputStream.readUTF();
            client.addInput(command);
        }
    }

    public void stop() {
        running = false;
    }
}
