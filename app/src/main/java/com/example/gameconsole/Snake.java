package com.example.gameconsole;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.io.IOException;

public class Snake extends AppCompatActivity {
    private BluetoothSocket btSocket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);

        ImageButton up = this.findViewById(R.id.arrow_up_snake);
        ImageButton down = this.findViewById(R.id.arrow_down_snake);
        ImageButton left = this.findViewById(R.id.arrow_left_snake);
        ImageButton right = this.findViewById(R.id.arrow_right_snake);
        btSocket = ((App) getApplication()).getBtSocket();

        setResult(RESULT_OK);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btSocket!=null)
                {
                    try
                    {
                        btSocket.getOutputStream().write("u".getBytes());
                    }
                    catch (IOException e)
                    {
                        ((App)getApplication()).msg("Error");
                    }
                }
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btSocket!=null)
                {
                    try
                    {
                        btSocket.getOutputStream().write("d".getBytes());
                    }
                    catch (IOException e)
                    {
                        ((App)getApplication()).msg("Error");
                    }
                }
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btSocket!=null)
                {
                    try
                    {
                        btSocket.getOutputStream().write("l".getBytes());
                    }
                    catch (IOException e)
                    {
                        ((App)getApplication()).msg("Error");
                    }
                }
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btSocket!=null)
                {
                    try
                    {
                        btSocket.getOutputStream().write("r".getBytes());
                    }
                    catch (IOException e)
                    {
                        ((App)getApplication()).msg("Error");
                    }
                }
            }
        });
    }
}
