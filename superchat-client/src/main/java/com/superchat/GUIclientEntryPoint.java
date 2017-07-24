package com.superchat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by adm on 17.07.2017.
 */
public class GUIclientEntryPoint extends Application



{





    public static void main(String[] args) {
        launch(args);

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();


        fxmlLoader.setLocation(this.getClass().getClassLoader().getResource("fxml/clientGUI.fxml"));

        Pane Pane = (Pane) fxmlLoader.load();
        Scene scene = new Scene(Pane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Chat");
        primaryStage.show();
    }
}