package com.superchat;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ivan on 6/20/17.
 */
public class ServerEntryPoint {

    private final static Logger logger = Logger.getLogger(ServerEntryPoint.class);

    public static void main(String[] args) {


        try {
            ServerSocket serverSocket = new ServerSocket(6081);

            while (true) {
                logger.info("Waiting for new client...");
                Socket socket = serverSocket.accept();

                logger.debug("Client socket joined, starting new thread");
                new ClientSocketThread(socket).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
