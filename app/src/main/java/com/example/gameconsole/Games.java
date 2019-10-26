package com.example.gameconsole;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class Games extends AppCompatActivity {
    BluetoothSocket btSocket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        Button maze = this.findViewById(R.id.maze);
        Button coloring = this.findViewById(R.id.coloring);
        Button snake = this.findViewById(R.id.snake);
        Button dj = this.findViewById(R.id.doodle_jump);
        btSocket = ((App) getApplication()).getBtSocket();

        maze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game("m");
                startActivityForResult(new Intent(Games.this, TypeOfMaze.class),150);

            }
        });
        coloring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game("c");
                startActivityForResult(new Intent(Games.this, Coloring.class),150);
            }
        });
        snake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game("s");
                startActivityForResult(new Intent(Games.this, Snake.class),150);
            }
        });
        dj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game("j");
                startActivityForResult(new Intent(Games.this, DoodleJump.class),150);
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

    void game(String game){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write(game.getBytes());
            }
            catch (IOException e)
            {
                ((App)getApplication()).msg("Error");
            }
        }
    }
}
