package com.example.gameconsole;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class Maze extends AppCompatActivity {
    private MainActivity.ThreadConnected thread;
    private App mApp;
    private Stopwatch stopwatch;

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
        setContentView(R.layout.activity_maze);


        ImageButton up = this.findViewById(R.id.arrow_up);
        ImageButton down = this.findViewById(R.id.arrow_down);
        ImageButton left = this.findViewById(R.id.arrow_left);
        ImageButton right = this.findViewById(R.id.arrow_right);
        TextView win_maze=this.findViewById(R.id.win_maze);
        up.setVisibility(View.VISIBLE);
        down.setVisibility(View.VISIBLE);
        left.setVisibility(View.VISIBLE);
        right.setVisibility(View.VISIBLE);
        win_maze.setVisibility(View.GONE);

        mApp=(App)this.getApplicationContext();
        thread = (MainActivity.ThreadConnected)((App) getApplicationContext()).getThread("bluetooth");

        if(((App) getApplicationContext()).getStopwatch()==null) {
            stopwatch = new Stopwatch();
            ((App)getApplicationContext()).setStopwatch(stopwatch);
        }
        else stopwatch = ((App) getApplicationContext()).getStopwatch();
        setResult(RESULT_OK);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!stopwatch.isRunning()) {
                    stopwatch.start();
                    ((App)getApplicationContext()).setStopwatch(stopwatch);
                }
                if (thread!=null)
                {
                    thread.write("u".getBytes());
                }
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!stopwatch.isRunning()) {
                    stopwatch.start();
                    ((App)getApplicationContext()).setStopwatch(stopwatch);
                }
                if (thread!=null)
                {
                    thread.write("d".getBytes());
                }
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!stopwatch.isRunning()) {
                    stopwatch.start();
                    ((App)getApplicationContext()).setStopwatch(stopwatch);
                }
                if (thread!=null)
                {
                    thread.write("l".getBytes());
                }
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!stopwatch.isRunning()) {
                    stopwatch.start();
                    ((App)getApplicationContext()).setStopwatch(stopwatch);
                }
                if (thread!=null)
                {
                    thread.write("r".getBytes());
                }
            }
        });
    }
}
