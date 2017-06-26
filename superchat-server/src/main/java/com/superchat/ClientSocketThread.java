package com.superchat;

import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

/**
 * Created by ivan on 6/20/17.
 */
public class ClientSocketThread extends Thread {

    private final static Logger logger = Logger.getLogger(ClientSocketThread.class);

    private Socket socket;
    private Map<String, Socket> clientsMap;

    public ClientSocketThread(final Socket socket, final Map<String, Socket> clientsMap) {
        this.socket = socket;
        this.clientsMap = clientsMap;
    }

    @Override
    public void run() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String login = dataInputStream.readUTF();

            for (Socket clientSocket : clientsMap.values()) {
                new DataOutputStream(clientSocket.getOutputStream()).writeUTF(login + " has just joined.");
            }


            clientsMap.put(login, socket);

            logger.info("User " + login + " has joined.");

            while (true) {
                String message = dataInputStream.readUTF();
                logger.info(login + " wrote: " + message);

                for (Map.Entry<String, Socket> clientEntry : clientsMap.entrySet()) {
                    String everyLogin = clientEntry.getKey();
                    Socket clientSocketThread = clientEntry.getValue();

                    OutputStream outputStream = clientSocketThread.getOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                    dataOutputStream.writeUTF(login + " wrote: " + message);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Socket getSocket() {
        return socket;
    }
}
