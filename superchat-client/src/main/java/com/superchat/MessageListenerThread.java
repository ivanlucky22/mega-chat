package com.superchat;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by ivan on 6/26/17.
 */
public class MessageListenerThread extends Thread {

    private DataInputStream dataInputStream;

    public MessageListenerThread(final DataInputStream dataInputStream) {

        this.dataInputStream = dataInputStream;
    }

    @Override
    public void run() {

        while (true) {
            try {
                String message = dataInputStream.readUTF();
                System.out.println(message);

            } catch (IOException e) {
               throw new IllegalStateException(e);
            }
        }

    }
}
