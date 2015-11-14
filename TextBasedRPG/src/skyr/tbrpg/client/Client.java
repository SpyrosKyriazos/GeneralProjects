/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Client {

    private Socket socket;
    private Collection<String> input;

    public Client() {
        input = new ArrayList<String>();
    }

    public void addInput(String command) {
        input.add(command);
    }

    public void init(String address) {
        try {
            System.out.println("Establishing connection. Please wait ...");
            socket = new Socket(address, 12345);
            System.out.println("Connected: " + socket);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sentOutput(String command) {
        try {
            new DataOutputStream(socket.getOutputStream()).writeUTF(command);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
