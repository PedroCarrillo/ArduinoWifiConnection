package com.pedrocarrillo.arduinocommunicationexample;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by pedrocarrillo on 10/6/16.
 */

public class ConnectionHandler implements Runnable {

    private WifiSocketClient wifiSocketClient;
    private Socket socket;
    private Boolean connected;


    public ConnectionHandler(WifiSocketClient wifiSocketClient) {
        this.wifiSocketClient = wifiSocketClient;
    }

    @Override
    public void run() {
        Log.e(wifiSocketClient.TAG, wifiSocketClient.address + " starting");

        socket = new Socket();
        BufferedReader inStream;
        try {
            socket.connect(new InetSocketAddress(wifiSocketClient.address, wifiSocketClient.port));
            connected = true;
            inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (connected) {
                String msg = inStream.readLine();
                Log.e("t", msg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        wifiSocketClient.execute();
    }

    public void sendMessage(String message) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            if (outputStream != null) {
                outputStream.write(message.getBytes());
                outputStream.write('\n');
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void disconnect() {
        connected = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WifiSocketClient getWifiSocketClient() {
        return wifiSocketClient;
    }

}
