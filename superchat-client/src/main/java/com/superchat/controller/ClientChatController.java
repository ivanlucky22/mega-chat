package com.superchat.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by adm on 17.07.2017.
 */
public class ClientChatController {

    @FXML
    TextField messageText;

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 6081;

    InetAddress inetAdres = InetAddress.getByName(HOST);
    Socket clientSocket = new Socket(inetAdres, PORT);

    OutputStream outputStream = clientSocket.getOutputStream();
    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);


    public void sendMessageButton(ActionEvent actionEvent) throws IOException {

        dataOutputStream.writeUTF(messageText.getText());

    }


    public ClientChatController() throws IOException {



    }

    public void contentWindowTextField(ActionEvent actionEvent) {

    }


    public void userListTextField(ActionEvent actionEvent) {

    }



    public void connectServerButton(ActionEvent actionEvent) {

    }

    public void disconnectServerButton(ActionEvent actionEvent) {

    }
}
