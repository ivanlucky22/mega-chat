package com.superchat;

import com.superchat.Thread.MessageListenerThread;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by ivan on 6/20/17.
 */
public class ClientEntryPoint extends Application{


    public static final String HOST = "127.0.0.1";
    public static final int PORT = 6081;
    private static DataOutputStream dataOutputStream;

    public static void main(String[] args) {

        launch(args);

        try {
            InetAddress inetAdres = InetAddress.getByName(HOST);
            Socket clientSocket = new Socket(inetAdres, PORT);

            OutputStream outputStream = clientSocket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            Scanner scanner = new Scanner(System.in);
            String line;

            System.out.println("Please write 'join' for connection server");
            writeJoin(dataOutputStream);

            System.out.println("Please write your login");
            dataOutputStream.writeUTF(scanner.nextLine());


            System.out.println("Do you wanna private or public message?");
            String messageChoice = scanner.nextLine();
            dataOutputStream.writeUTF(messageChoice);

            if (messageChoice.equalsIgnoreCase("private")) {
                System.out.println("Who's your friend?");
                dataOutputStream.writeUTF(scanner.nextLine());
            } else if (messageChoice.equalsIgnoreCase("public")) {

            }

            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            new MessageListenerThread(dataInputStream).start();


            System.out.println("Print your messages");
            while ((line = scanner.nextLine()) != null) {
                dataOutputStream.writeUTF(line);
                dataOutputStream.writeUTF(messageChoice);
                if (messageChoice.equalsIgnoreCase("private")) {
                    System.out.println("Who's your friend?");
                    dataOutputStream.writeUTF(scanner.nextLine());
                    System.out.println("Print your messages");
                } else if (messageChoice.equalsIgnoreCase("public")) {
                    System.out.println("Print your messages");
                }
            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeJoin(DataOutputStream dataOutputStream) throws IOException {
        ClientEntryPoint.dataOutputStream = dataOutputStream;
        dataOutputStream.writeUTF("join");
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("view/startwindow.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void join(ActionEvent actionEvent) {
        try {
            writeJoin(dataOutputStream);
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
}
