    package com.example.gameconsole;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class TypeOfMaze extends AppCompatActivity {
    BluetoothSocket btSocket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_maze);

        Button withLight = findViewById(R.id.with_light);
        Button woLight = findViewById(R.id.wo_light);
        btSocket = ((App) getApplication()).getBtSocket();

        setResult(RESULT_OK);
        withLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(TypeOfMaze.this, Maze.class),150);
                if (btSocket!=null)
                {
                    try
                    {
                        btSocket.getOutputStream().write("n".getBytes());
                    }
                    catch (IOException e)
                    {
                        ((App)getApplication()).msg("Error");
                    }
                }
            }
        });

        woLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(TypeOfMaze.this, Maze.class),150);
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==150&&resultCode==RESULT_OK) {
            Toast.makeText(getApplicationContext(),"Im dead inside",Toast.LENGTH_LONG).show();
            try {
                btSocket.getOutputStream().write("b".getBytes());
            } catch (IOException e) {
                ((App) getApplication()).msg("Error");
            }
        }
    }
}
