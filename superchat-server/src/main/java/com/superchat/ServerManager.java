package com.superchat;

import controller.ServerChatController;
import org.apache.log4j.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ivan on 6/20/17.
 */
public class ServerManager implements Runnable {

    private final static Logger logger = Logger.getLogger(ServerManager.class);

    static Map<String, Socket> clientsMap = new HashMap<String, Socket>();
    private static boolean aBoolean = true;
    private static ServerSocket serverSocket;
    ServerChatController serverChatController;

    public  void startServer() throws IOException {


        serverChatController= new ServerChatController();
        int port = serverChatController.getPort();
        serverSocket = new ServerSocket(port);

        aBoolean = true;
        while (aBoolean) {
            logger.info("Waiting for new client...");
            Socket socket = serverSocket.accept();
            logger.info("Client socket joined, starting new thread");
            ClientSocketThread clientSocketThread = new ClientSocketThread(socket, clientsMap);
            clientSocketThread.start();

        }

        serverSocket.close();
    }

    public void stopServer() {
        aBoolean = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            startServer();
        } catch (SocketException e) {
            logger.info(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

