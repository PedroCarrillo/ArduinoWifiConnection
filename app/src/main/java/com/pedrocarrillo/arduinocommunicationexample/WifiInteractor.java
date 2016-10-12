package com.pedrocarrillo.arduinocommunicationexample;

/**
 * Created by pedrocarrillo on 10/6/16.
 */

public interface WifiInteractor {

    void connected();
    void disconnected();
    void gotMessage(String message);

}
