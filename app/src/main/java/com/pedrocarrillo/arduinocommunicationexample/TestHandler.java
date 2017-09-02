package com.pedrocarrillo.arduinocommunicationexample;

import android.util.Log;

/**
 * Created by pedrocarrillo on 11/8/16.
 */

public class TestHandler implements Runnable {

    int number = 0;

    @Override
    public void run() {
        Log.e("TEST", " "+number);
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
