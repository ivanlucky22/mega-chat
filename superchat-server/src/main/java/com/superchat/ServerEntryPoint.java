package com.superchat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ivan on 6/20/17.
 */
public class ServerEntryPoint extends Application{

    private final static Logger logger = Logger.getLogger(ServerEntryPoint.class);

    static Map<String, Socket> clientsMap;
    private static ServerSocket serverSocket;

    static {
        try {
            serverSocket = new ServerSocket(6081);
            clientsMap = new HashMap<String, Socket>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        launch(args);

    }

    public static void stopServer() throws IOException {
        logger.info("Server is stopped");
        if(!serverSocket.isClosed()){
            serverSocket.close();
        }

    }

    public static void startServer() throws IOException {


        while (true) {
            logger.info("Waiting for new client...");
           Socket socket =serverSocket.accept();
            logger.debug("Client socket joined, starting new thread");
            ClientSocketThread clientSocketThread = new ClientSocketThread(socket, clientsMap);
            clientSocketThread.start();
        }
    }



    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("view/serverOnWindow.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
