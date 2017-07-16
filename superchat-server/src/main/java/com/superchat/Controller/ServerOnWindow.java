package com.superchat.Controller;

import com.superchat.ServerEntryPoint;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * Created by lodo4ka on 16.07.17.
 */
public class ServerOnWindow {


    @FXML
    Label label;

    public void start(ActionEvent actionEvent) {
        try {
            ServerEntryPoint.startServer();
            label.setText("Server is running");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(ActionEvent actionEvent) {

        ServerEntryPoint.stopServer();
        label.setText("Server is closed");

    }
}
