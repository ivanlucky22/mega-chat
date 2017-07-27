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
    private String login;

    public String getLogin() {
        return login;
    }

    public ClientSocketThread(final Socket socket, final Map<String, Socket> clientsMap) {
        this.socket = socket;
        this.clientsMap = clientsMap;
    }

    @Override
    public void run() {
        try {

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());


            while (true) {
                final String message = dataInputStream.readUTF();
                switch (message) {

                    case "join":
                        clientJoin(dataInputStream);
                        break;
                    case "private":
                        writePrivateMessage(dataInputStream);
                        break;
                    case "public":
                        writePublicMessage(dataInputStream);
                        break;
                    default:
                        logger.warn("Command not recognize");
                        throw new IllegalArgumentException("Command not recognize");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writePublicMessage(DataInputStream dataInputStream) throws IOException {


        String message = dataInputStream.readUTF();
        logger.info(login + " wrote: " + message);


        for (Map.Entry<String, Socket> clientEntry : clientsMap.entrySet()) {
            Socket clientSocketThread = clientEntry.getValue();


            OutputStream outputStream = clientSocketThread.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            dataOutputStream.writeUTF(login + " wrote: " + message);
        }
    }

    private void clientJoin(DataInputStream dataInputStream) throws IOException {
        login = dataInputStream.readUTF();
        clientsMap.put(login, this.socket);

        logger.info("User " + login + " has joined.");
    }

    protected void writePrivateMessage(DataInputStream dataInputStream) throws IOException {
        String friend = dataInputStream.readUTF();
        String message = dataInputStream.readUTF();

        Socket friendSocket = clientsMap.get(friend);
        if (friendSocket != null) {
            String fullMessage = login + " send message:\n" + message;
            logger.info(fullMessage);
            new DataOutputStream(friendSocket.getOutputStream()).writeUTF(fullMessage);
        } else {
            logger.info(friend + " hasn't been found");
        }
    }

}
