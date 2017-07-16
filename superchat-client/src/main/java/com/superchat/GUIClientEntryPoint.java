package com.superchat;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * Created by adm on 16.07.2017.
 */
public class GUIClientEntryPoint extends Application

{


    public static void main(String[] args) {
        launch(args);

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();


        fxmlLoader.setLocation(this.getClass().getClassLoader().getResource("fxml/clientChat.fxml"));

        Pane clientPane = (Pane) fxmlLoader.load();
        Scene scene = new Scene(clientPane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Chat Client");
        primaryStage.show();
    }
}
