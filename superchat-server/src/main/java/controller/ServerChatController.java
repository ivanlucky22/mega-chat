package controller;

import com.superchat.ServerManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;


/**
 * Created by adm on 17.07.2017.
 */
public class ServerChatController{

    ServerManager serverManager = new ServerManager();
    Thread serverThread = new Thread(serverManager);


    @FXML
    TextField chatLog;


    @FXML
    TextField  portConnect = new TextField();


    @FXML
    private void initialize(){
        portConnect.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    portConnect.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void connectServerButton(ActionEvent actionEvent) {


        if (!serverThread.isAlive()) {
            getPort();
            serverThread.start();
            Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setTitle("INFO");
            dialog.setHeaderText("Server is running!");
            dialog.showAndWait();
        }
        chatLog.setText("Waiting for new client...");
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

    public int getPort() {

        String text = portConnect.getText();
        int i = stringToInt(text);
        return i;
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
