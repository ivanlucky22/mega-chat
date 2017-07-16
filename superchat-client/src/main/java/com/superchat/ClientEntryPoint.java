package com.superchat;

import com.superchat.Thread.MessageListenerThread;
import javafx.application.Application;
import javafx.event.ActionEvent;
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
public class ClientEntryPoint extends Application {


    private static final String HOST = "127.0.0.1";
    private static final int PORT = 6081;
    private static Scanner scanner;
    private static DataOutputStream dataOutputStream;
    private static OutputStream outputStream;
    private static Socket clientSocket;
    private static InetAddress inetAdres;

    static {
        try {
            inetAdres = InetAddress.getByName(HOST);
            clientSocket = new Socket(inetAdres, PORT);
            outputStream = clientSocket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);
            scanner = new Scanner(System.in);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

        launch(args);

        try {

            String line;

            System.out.println("Please write 'join' for connection server");
            writeJoin(dataOutputStream);

            System.out.println("Write your login");

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

    public static void writeLogin(DataOutputStream dataOutputStream, String text) throws IOException {
        System.out.println("Please write your login");
        dataOutputStream.writeUTF(text);
    }

    private static void writeJoin(DataOutputStream dataOutputStream) throws IOException {
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


    public void pressJoin(ActionEvent actionEvent) {
        try {
            writeJoin(dataOutputStream);
            changeStage(actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void changeStage(ActionEvent actionEvent) throws IOException {
        Parent secondWindow = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        Scene secondScene = new Scene(secondWindow);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.hide();
        appStage.setScene(secondScene);
        appStage.show();
    }

    public static DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

}
