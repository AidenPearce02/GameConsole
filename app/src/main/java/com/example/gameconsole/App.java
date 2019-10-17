package com.example.gameconsole;

import android.app.Application;
import android.bluetooth.BluetoothSocket;

public abstract class App extends Application {
    private BluetoothSocket btSocket;

    public BluetoothSocket getBtSocket() {
        return btSocket;
    }

    public void setBtSocket(BluetoothSocket btSocket) {
        this.btSocket = btSocket;
    }
}
