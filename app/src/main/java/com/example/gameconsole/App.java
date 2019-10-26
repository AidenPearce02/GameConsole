package com.example.gameconsole;

import android.app.Application;
import android.bluetooth.BluetoothSocket;
import android.widget.Toast;

public class App extends Application {
    private BluetoothSocket btSocket;

    public BluetoothSocket getBtSocket() {
        return btSocket;
    }

    public void setBtSocket(BluetoothSocket btSocket) {
        this.btSocket = btSocket;
    }

    public void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
}
