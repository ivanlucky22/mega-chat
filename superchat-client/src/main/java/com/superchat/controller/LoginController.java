package com.superchat.controller;

import com.superchat.ClientEntryPoint;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by lodo4ka on 13/07/2017.
 */
public class LoginController {


    @FXML
    TextField loginField;


    public void onAction(ActionEvent actionEvent) {
        pressButton();
        chaneStage(actionEvent);
    }

    private void chaneStage(ActionEvent actionEvent) {
        try {
            Parent secondWindow = FXMLLoader.load(getClass().getResource("/view/messageWindow.fxml"));
            Scene secondScene = new Scene(secondWindow);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.hide();
            appStage.setScene(secondScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pressButton() {
        DataOutputStream dataOutputStream = ClientEntryPoint.getDataOutputStream();
        try {
            ClientEntryPoint.writeLogin(dataOutputStream, loginField.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
