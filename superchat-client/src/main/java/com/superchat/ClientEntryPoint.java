package com.superchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by ivan on 6/20/17.
 */
public class ClientEntryPoint{


    private static final String HOST = "127.0.0.1";
    private static final int PORT = 6081;


    public static void main(String[] args) {

        try {
            InetAddress inetAdres = InetAddress.getByName(HOST);
            Socket clientSocket = new Socket(inetAdres, PORT);

            OutputStream outputStream = clientSocket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            Scanner scanner = new Scanner(System.in);
            String line;

            System.out.println("Please write 'join' for connection server");
            dataOutputStream.writeUTF(scanner.nextLine());

            System.out.println("Please write your login");
            dataOutputStream.writeUTF(scanner.nextLine());

            System.out.println("Do you wanna private or public message?");
            String messageChoice = scanner.nextLine();
            dataOutputStream.writeUTF(messageChoice);

            if (messageChoice.equalsIgnoreCase("private")) {
                System.out.println("Who's your friend?");
                dataOutputStream.writeUTF(scanner.nextLine());
            } else if (messageChoice.equalsIgnoreCase("public")) {
                System.out.println();
            }

            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            new MessageListenerThread(dataInputStream).start();


            System.out.println("Print your messages");
            while ((line = scanner.nextLine()) != null) {
                dataOutputStream.writeUTF(line);
                dataOutputStream.writeUTF(messageChoice);
                if (messageChoice.equalsIgnoreCase("private")) {
                    System.out.println("Who's your friend?");
                    dataOutputStream.writeUTF(scanner.nextLine());
                    System.out.println("Print your messages");
                } else if (messageChoice.equalsIgnoreCase("public")) {
                    System.out.println("Print your messages");
                }
            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
