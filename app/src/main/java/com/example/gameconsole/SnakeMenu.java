package com.example.gameconsole;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class SnakeMenu extends AppCompatActivity {
    private BluetoothSocket btSocket;
    private TextView speedNow,mazeNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_menu);
        Button game= this.findViewById(R.id.startGame);
        Button maze=this.findViewById(R.id.typeWall);
        Button speed=this.findViewById(R.id.level);
        speedNow=this.findViewById(R.id.speedNow);
        mazeNow=this.findViewById(R.id.mazeNow);
        btSocket=((App)getApplication()).getBtSocket();

        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice("g");
                startActivityForResult(new Intent(SnakeMenu.this, Snake.class),145);
            }
        });
        maze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice("m");
                startActivityForResult(new Intent(SnakeMenu.this, TypeOfSnakeMaze.class),155);
            }
        });
        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice("l");
                startActivityForResult(new Intent(SnakeMenu.this, Levels.class),150);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==145&&resultCode==RESULT_OK) {
            finish();
            if(btSocket!=null) {
                try {
                    btSocket.getOutputStream().write("b".getBytes());

                } catch (IOException e) {
                    ((App) getApplication()).msg("Error");
                }
            }
        }
        else if(requestCode==150&&resultCode==RESULT_OK){
            speedNow.setText(data.getStringExtra(getResources().getString(R.string.speedNow)));
        }
        else if(requestCode==155&&resultCode==RESULT_OK){
            mazeNow.setText(data.getStringExtra(getResources().getString(R.string.mazeNow)));
        }
    }

    void choice(String choiceS){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write(choiceS.getBytes());
            }
            catch (IOException e)
            {
                ((App)getApplication()).msg("Error");
            }
        }
    }
}
