package com.example.gameconsole;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private App mApp;
    private ThreadConnected myThreadConnected;
    private ThreadConnectBTdevice myThreadConnectBTdevice;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApp = (App)this.getApplicationContext();
        Button connect = this.findViewById(R.id.connect);
        Button games = this.findViewById(R.id.games);
        Button about = this.findViewById(R.id.about);
        Button effects=this.findViewById(R.id.effects);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this,DeviceList.class),200);
            }
        });
        games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Games.class));
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, About.class));

            }
        });
        effects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myThreadConnected!=null)
                {
                    myThreadConnected.write("e".getBytes());
                }
                startActivityForResult(new Intent(MainActivity.this,Effects.class),150);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mApp.setCurrentActivity(this);
    }

    @Override
    protected void onPause() {
        clearReferences();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearReferences();
        if(myThreadConnectBTdevice!=null) myThreadConnectBTdevice.cancel();
    }

    private void clearReferences(){
        Activity currActivity = mApp.getCurrentActivity();
        if (this.equals(currActivity))
            mApp.setCurrentActivity(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200&&resultCode==RESULT_OK){
            String address = data.getStringExtra(getResources().getString(R.string.device_address));
            BluetoothDevice btDevice= BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address);
            myThreadConnectBTdevice = new ThreadConnectBTdevice(btDevice);
            myThreadConnectBTdevice.start();
        }
        if(requestCode==150){
            if (myThreadConnected!=null)
            {
                myThreadConnected.write("b".getBytes());
            }
        }
    }

    private class ThreadConnectBTdevice extends Thread {

        private BluetoothSocket bluetoothSocket = null;

        private ThreadConnectBTdevice(BluetoothDevice device) {

            try {
                bluetoothSocket = device.createRfcommSocketToServiceRecord(myUUID);
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void run() {

            boolean success = false;

            try {
                bluetoothSocket.connect();
                success = true;
            }

            catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.connection_fail), Toast.LENGTH_LONG).show();
                    }
                });

                finish();
                try {
                    bluetoothSocket.close();
                }
                catch (IOException e1) {

                    e1.printStackTrace();
                }
            }

            if(success) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,getResources().getString(R.string.connected),Toast.LENGTH_SHORT).show();
                    }
                });
                myThreadConnected = new ThreadConnected(bluetoothSocket);
                myThreadConnected.setName("bluetooth");
                myThreadConnected.start();
            }
        }


        void cancel() {

            Toast.makeText(getApplicationContext(), "Close - BluetoothSocket", Toast.LENGTH_LONG).show();

            try {
                bluetoothSocket.close();
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class ThreadConnected extends Thread {    // Поток - приём и отправка данных

        private final InputStream connectedInputStream;
        private final OutputStream connectedOutputStream;
        private StringBuilder sb = new StringBuilder();
        private String message;
        private Activity mApp;
        private Stopwatch stopwatch;
        ThreadConnected(BluetoothSocket socket) {
            InputStream in = null;
            OutputStream out = null;
            message="";

            try {
                in = socket.getInputStream();
                out = socket.getOutputStream();
            }

            catch (IOException e) {
                e.printStackTrace();
            }

            connectedInputStream = in;
            connectedOutputStream = out;
        }

        private void changeMaze(){
            mApp.findViewById(R.id.arrow_left).setVisibility(View.GONE);
            mApp.findViewById(R.id.arrow_right).setVisibility(View.GONE);
            mApp.findViewById(R.id.arrow_up).setVisibility(View.GONE);
            mApp.findViewById(R.id.arrow_down).setVisibility(View.GONE);
            TextView win_maze = mApp.findViewById(R.id.win_maze);
            win_maze.setText(String.valueOf(stopwatch.getElapsedTimeSecs()));
            win_maze.setVisibility(View.VISIBLE);
        }

        private void changeSnake(){
            mApp.findViewById(R.id.arrow_left_snake).setVisibility(View.GONE);
            mApp.findViewById(R.id.arrow_right_snake).setVisibility(View.GONE);
            mApp.findViewById(R.id.arrow_up_snake).setVisibility(View.GONE);
            mApp.findViewById(R.id.arrow_down_snake).setVisibility(View.GONE);
            TextView win_snake = mApp.findViewById(R.id.win_snake);
            win_snake.setText(message.substring(2));
            win_snake.setVisibility(View.VISIBLE);
        }

        @Override
        public void run() { // Приём данных

            while (true) {
                try {
                    byte[] buffer = new byte[1];
                    int bytes = connectedInputStream.read(buffer);
                    String strIncom = new String(buffer, 0, bytes);
                    sb.append(strIncom);
                    int endOfLineIndex = sb.indexOf("\r\n");

                    if (endOfLineIndex > 0) {
                        message = sb.substring(0, endOfLineIndex);
                        sb.delete(0, sb.length());
                        while(((App)getApplicationContext()).getCurrentActivity()==null) Thread.sleep(10);
                        if(((App)getApplicationContext()).getCurrentActivity()!=null) {
                            mApp = ((App) getApplicationContext()).getCurrentActivity();
                            if(message.charAt(0)=='m'){
                                if(((App)getApplicationContext()).getStopwatch()!=null){
                                    stopwatch =((App)getApplicationContext()).getStopwatch();
                                    if(stopwatch.isRunning()){
                                        mApp.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if(mApp.getClass().getSimpleName().equals("Maze")) {
                                                    changeMaze();
                                                    Handler handler = new Handler();
                                                    handler.postDelayed(new Runnable() {
                                                        public void run() {
                                                            mApp.finish();
                                                        }
                                                    }, 5000);
                                                    //5 seconds
                                                }
                                                stopwatch.pause();
                                                ((App)getApplicationContext()).setStopwatch(null);
                                            }
                                        });
                                    }
                                }
                            }
                            else if(message.charAt(0)=='s'){
                                mApp.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(mApp.getClass().getSimpleName().equals("Snake")) {
                                            changeSnake();
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                public void run() {
                                                    mApp.finish();
                                                }
                                            }, 5000);
                                        }
                                        stopwatch.pause();
                                        ((App)getApplicationContext()).setStopwatch(null);
                                    }
                                });
                            }
                            else {
                                mApp.runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(mApp, message, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }
                } catch (IOException e) {
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        public void write(byte[] buffer) {
            try {
                connectedOutputStream.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
