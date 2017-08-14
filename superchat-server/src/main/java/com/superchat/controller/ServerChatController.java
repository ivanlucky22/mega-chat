package com.superchat.controller;

import com.superchat.ServerManager;

import com.superchat.TextAreaAppender;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;


/**
 * Created by adm on 17.07.2017.
 */
public class ServerChatController{

    ServerManager serverManager = new ServerManager();
    Thread serverThread = new Thread(serverManager);



    @FXML
    public  TextArea textArea = new TextArea();



    @FXML
    TextField  portConnect;


    public ServerChatController() {
        TextAreaAppender.setTextArea(textArea);
    }

    public void connectServerButton(ActionEvent actionEvent) {



        if (!serverThread.isAlive()) {
            serverManager.setPort(stringToInt(portConnect.getText()));
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



    private int stringToInt(String inputString){
        try {
            return Integer.parseInt(inputString);
        }
        catch (NumberFormatException e){
            e.printStackTrace();
            return -1;
        }
    }
}
