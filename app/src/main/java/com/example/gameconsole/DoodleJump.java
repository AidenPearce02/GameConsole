package com.example.gameconsole;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.io.IOException;

public class DoodleJump extends AppCompatActivity {
    BluetoothSocket btSocket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doodle_jump);

        ImageButton left = this.findViewById(R.id.arrow_left_dj);
        ImageButton right = this.findViewById(R.id.arrow_right_dj);
        btSocket = ((App) getApplication()).getBtSocket();
        setResult(RESULT_OK);

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
