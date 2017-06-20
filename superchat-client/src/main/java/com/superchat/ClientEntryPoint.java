package com.superchat;

import javax.sound.midi.Soundbank;
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
public class ClientEntryPoint {


    public static final String HOST = "127.0.0.1";
    public static final int PORT = 6081;

    public static void main(String[] args) {

        try {
            InetAddress inetAdres = InetAddress.getByName(HOST);
            Socket clientSocket = new Socket(inetAdres, PORT);

            OutputStream outputStream = clientSocket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            Scanner scanner = new Scanner(System.in);
            String line;

            System.out.println("Enter your name");
            dataOutputStream.writeUTF(scanner.nextLine());

            System.out.println("Print your messages");
            while ((line = scanner.nextLine()) != null) {
                dataOutputStream.writeUTF(line);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
