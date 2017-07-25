package com.superchat;

import org.apache.log4j.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ivan on 6/20/17.
 */
public class ServerManager implements Runnable {

    private final static Logger logger = Logger.getLogger(ServerManager.class);

    static Map<String, Socket> clientsMap = new HashMap<String, Socket>();
    private static boolean aBoolean = true;


    public static void startServer() throws IOException {


        ServerSocket serverSocket = new ServerSocket(6081);

        aBoolean = true;
        while (aBoolean) {
            logger.info("Waiting for new client...");
            Socket socket = serverSocket.accept();
            logger.debug("Client socket joined, starting new thread");
            ClientSocketThread clientSocketThread = new ClientSocketThread(socket, clientsMap);
            clientSocketThread.start();


            if (stopServer()) {
                serverSocket.close();
                socket.close();
            }
        }
    }

    public static boolean stopServer() {
        return aBoolean = false;
    }

    @Override
    public void run() {
        try {
            startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

