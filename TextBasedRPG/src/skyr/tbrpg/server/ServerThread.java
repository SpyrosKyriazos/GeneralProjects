/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyr.tbrpg.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Spyros
 */
public class ServerThread implements Runnable {

    private boolean running = true;
    private ServerSocket server;
    private int port = 12345;
    private int id = 1;

    @Override
    public void run() {
        initSockets();
        ServerGameThread gameThreadRunnable = new ServerGameThread();
        Thread gameThread = new Thread(gameThreadRunnable);
        gameThread.start();
        ServerOutputThread outThreadRunnable = new ServerOutputThread();
        Thread outThread = new Thread(outThreadRunnable);
        outThread.start();
        while (running) {
            try {
                System.out.println("waiting connection ...");
                Socket s = server.accept();
                ServerInputThread inputThreadRunnable = new ServerInputThread(s, id, gameThreadRunnable);
                Thread inputThread = new Thread(inputThreadRunnable);
                inputThread.start();
                id++;
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void initSockets() {
        try {
            System.out.println("Binding to port " + port + ", please wait  ...");
            server = new ServerSocket(port);
            System.out.println("Server started: " + server);
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stop() {
        running = false;
    }
}
