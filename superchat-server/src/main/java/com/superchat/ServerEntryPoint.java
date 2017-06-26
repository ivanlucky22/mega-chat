package com.superchat;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ivan on 6/20/17.
 */
public class ServerEntryPoint {

    private final static Logger logger = Logger.getLogger(ServerEntryPoint.class);

    static Map<String, Socket> clientsMap = new HashMap<String, Socket>();

    public static void main(String[] args) {


        try {
            ServerSocket serverSocket = new ServerSocket(6081);

            while (true) {
                logger.info("Waiting for new client...");
                Socket socket = serverSocket.accept();
                logger.debug("Client socket joined, starting new thread");

                ClientSocketThread clientSocketThread = new ClientSocketThread(socket, clientsMap);
                clientSocketThread.start();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
