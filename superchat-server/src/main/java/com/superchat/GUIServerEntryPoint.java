package com.superchat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by adm on 15.07.2017.
 */
public class GUIServerEntryPoint  extends Application

{


    public static void main(String[] args) {
        launch(args);

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();


        fxmlLoader.setLocation(this.getClass().getClassLoader().getResource("fxml/serverChat.fxml"));


        Pane pane = (Pane) fxmlLoader.load();
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Client Chat");
        primaryStage.show();
    }
}
