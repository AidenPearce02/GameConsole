package com.example.gameconsole;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Games extends AppCompatActivity {

    private App mApp;
    private MainActivity.ThreadConnected thread;

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
    }

    private void clearReferences(){
        Activity currActivity = mApp.getCurrentActivity();
        if (this.equals(currActivity))
            mApp.setCurrentActivity(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        Button maze = this.findViewById(R.id.maze);
        Button coloring = this.findViewById(R.id.coloring);
        Button snake = this.findViewById(R.id.snake);
        Button dj = this.findViewById(R.id.doodle_jump);

        thread=(MainActivity.ThreadConnected)((App)getApplicationContext()).getThread();
        mApp=(App)this.getApplicationContext();

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
                startActivityForResult(new Intent(Games.this, SnakeMenu.class),150);
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
            if(thread!=null) {
                thread.write("b".getBytes());
            }
        }
    }

    void game(String game){
        if (thread!=null)
        {
            thread.write(game.getBytes());
        }
    }
}
