package com.superchat.Controller;

import com.superchat.ServerEntryPoint;
import javafx.event.ActionEvent;

import java.io.IOException;

/**
 * Created by lodo4ka on 16.07.17.
 */
public class ServerOnWindow {
    public void start(ActionEvent actionEvent) {
        try {
            ServerEntryPoint.startServer();
       } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(ActionEvent actionEvent) {
        try {
            ServerEntryPoint.stopServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
