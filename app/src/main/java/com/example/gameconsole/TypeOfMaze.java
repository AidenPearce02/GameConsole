package com.example.gameconsole;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class TypeOfMaze extends AppCompatActivity {
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
        setContentView(R.layout.activity_type_of_maze);

        Button withLight = findViewById(R.id.with_light);
        Button woLight = findViewById(R.id.wo_light);

        thread = (MainActivity.ThreadConnected)((App) getApplication()).getThread("bluetooth");
        mApp=(App)this.getApplicationContext();

        setResult(RESULT_OK);
        withLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(TypeOfMaze.this, Maze.class),150);
                if (thread!=null)
                {
                    thread.write("n".getBytes());
                }
            }
        });

        woLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(TypeOfMaze.this, Maze.class),150);
                if (thread!=null)
                {
                    thread.write("l".getBytes());
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==150&&resultCode==RESULT_OK) {
            finish();
            if(thread!=null) {
                thread.write("b".getBytes());
            }
        }
    }
}
