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
public class ServerEntryPoint extends Application {

    private final static Logger logger = Logger.getLogger(ServerEntryPoint.class);

    static Map<String, Socket> clientsMap = new HashMap<String, Socket>();


    public static void main(String[] args) {

        launch(args);

    }


    public static void startServer() throws IOException {


        ServerSocket serverSocket = new ServerSocket(6081);

        while (true) {
            logger.info("Waiting for new client...");
            Socket socket = serverSocket.accept();
            logger.debug("Client socket joined, starting new thread");
            ClientSocketThread clientSocketThread = new ClientSocketThread(socket, clientsMap);
            clientSocketThread.start();

            if (stopServer()) {
                serverSocket.close();
                socket.close();
            }
        }
    }

    public static boolean stopServer() {
        return true;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
//
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/serverGUI.fxml"));
//        Parent root = (Parent) fxmlLoader.load();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.show();
//    }
    }
}
