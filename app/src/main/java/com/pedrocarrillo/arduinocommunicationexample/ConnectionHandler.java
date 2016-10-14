package com.pedrocarrillo.arduinocommunicationexample;

import android.util.Log;

/**
 * Created by pedrocarrillo on 10/6/16.
 */

public class ConnectionHandler implements Runnable {

    WifiSocketClient wifiSocketClient;

    public ConnectionHandler(WifiSocketClient wifiSocketClient) {
        this.wifiSocketClient = wifiSocketClient;
    }

    @Override
    public void run() {
        Log.e(wifiSocketClient.TAG, wifiSocketClient.address + " starting");
        wifiSocketClient.execute();
    }

    public WifiSocketClient getWifiSocketClient() {
        return wifiSocketClient;
    }

}
