package com.pedrocarrillo.arduinocommunicationexample;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by pedrocarrillo on 10/2/16.
 */

public class ConnectivityManager {

    public static String getIpAddress(Context context) {
        try {
            WifiManager wm = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
            String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
            return ip;
        } catch (Exception e) {
            return null;
        }

    }

}
