package controller;

import com.superchat.ServerManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Created by adm on 17.07.2017.
 */
public class ServerChatController {


    ServerManager serverManager = new ServerManager();
    Thread serverThread = new Thread(serverManager);


    @FXML
    TextField chatLog;

    public void connectServerButton(ActionEvent actionEvent) {
        try {
            ServerManager.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnectServerButton(ActionEvent actionEvent) {
        ServerManager.stopServer();
    }

    public void chatLog(ActionEvent actionEvent) {

    }
}
