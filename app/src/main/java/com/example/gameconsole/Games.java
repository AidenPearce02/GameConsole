package com.example.gameconsole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Games extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        Button maze = this.findViewById(R.id.maze);
        Button coloring = this.findViewById(R.id.coloring);
        Button snake = this.findViewById(R.id.snake);
        Button dj = this.findViewById(R.id.doodle_jump);
        maze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Games.this, Maze.class));
            }
        });
        coloring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Games.this, Coloring.class));
            }
        });
        snake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Games.this, Snake.class));
            }
        });
        dj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Games.this, DoodleJump.class));
            }
        });
    }
}
