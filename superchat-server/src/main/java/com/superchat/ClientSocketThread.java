package com.superchat;

import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by ivan on 6/20/17.
 */
public class ClientSocketThread extends Thread {

    private final static Logger logger = Logger.getLogger(ClientSocketThread.class);

    private Socket socket;

    public ClientSocketThread(final Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String name = dataInputStream.readUTF();
            logger.info("User " + name + " has joined.");

            while (true) {
                String message = dataInputStream.readUTF();
                logger.info(name + " wrote: " + message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
