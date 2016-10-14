package com.pedrocarrillo.arduinocommunicationexample;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WifiInteractor {

    private final String TAG = getClass().getSimpleName();
    int i = 0;

    // AsyncTask object that manages the connection in a separate thread
    List<ConnectionHandler> connectionHandlerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.e(TAG, ConnectivityManager.getIpAddress(this));

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                if (i%2 == 0) {
                    connectionHandlerList.get(0).getWifiSocketClient().sendMessage("1");
//                    wifiSocketClients.get(1).sendMessage("0");
                } else {
                    connectionHandlerList.get(0).getWifiSocketClient().sendMessage("0");
//                    wifiSocketClients.get(1).sendMessage("1");
//                    wifiSocketClients.get(0).sendMessage("0");
                }
            }
        });


//        if(wifiTask != null) {
//            setStatus("Already connected!");
//            return;
//        }

        try {
            // Get the remote host from the UI and start the thread
//            String host = "192.168.1.14";
            String host = "192.168.43.47";
            int port = 23;

            WifiSocketClient first = new WifiSocketClient(host, port, this);
            connectionHandlerList.add(new ConnectionHandler(first));
//            first.execute();
//            wifiSocketClients.add(first);

//            String host2 = "192.168.1.18";
//            int port2 = 24;
//            WifiSocketClient second = new WifiSocketClient(host2, port2, this);
//            connectionHandlerList.add(new ConnectionHandler(second));
//            wifiSocketClients.add(second);


            for (final ConnectionHandler connectionHandler : connectionHandlerList) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                         new Thread(connectionHandler).start();
                    }
                }, 5000);

            }


        } catch (Exception e) {
            e.printStackTrace();
            setStatus("Invalid address/port!");
        }

    }

    void setStatus(String s) {
        Log.v(TAG, s);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Invoked by the AsyncTask when the connection is successfully established.
     */
    @Override
    public void connected() {
        setStatus("Connected.");
    }

    /**
     * Invoked by the AsyncTask when the connection ends..
     */
    @Override
    public void disconnected() {
//        setStatus("Disconnected.");
//        for (WifiSocketClient wifiTask : wifiSocketClients) {
//           wifiTask = null;
//        }

    }

    /**
     * Invoked by the AsyncTask when a newline-delimited message is received.
     */
    @Override
    public void gotMessage(String msg) {
        Log.v(TAG, "[RX] " + msg);
    }


}
