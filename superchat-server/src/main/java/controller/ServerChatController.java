package controller;

import com.superchat.ServerManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by adm on 17.07.2017.
 */
public class ServerChatController{

    ServerManager serverManager = new ServerManager();
    Thread serverThread = new Thread(serverManager);

    @FXML
    TextField chatLog = new TextField();

    @FXML
    TextField portConnect = new TextField();



    public void connectServerButton(ActionEvent actionEvent) {

        if (!serverThread.isAlive()) {
            serverThread.start();
            Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setTitle("INFO");
            dialog.setHeaderText("Server is running!");
            dialog.showAndWait();
        }

    }

    public void disconnectServerButton(ActionEvent actionEvent) {


        if (serverThread.isAlive()) {
            serverManager.stopServer();
            Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setTitle("INFO");
            dialog.setHeaderText("Server stopped!");
            dialog.showAndWait();
        }
    }

    public int getPort() {
        try {
            int port = Integer.parseInt(portConnect.getText());
            return port;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
