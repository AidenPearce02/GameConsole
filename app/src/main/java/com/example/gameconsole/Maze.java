package com.example.gameconsole;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class Maze extends AppCompatActivity {
    private MainActivity.ThreadConnected thread;
    private App mApp;
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

        mApp=(App)this.getApplicationContext();
        if(mApp!=null){
            Toast.makeText(getApplicationContext(),"true",Toast.LENGTH_SHORT).show();
        }
        ImageButton up = this.findViewById(R.id.arrow_up);
        ImageButton down = this.findViewById(R.id.arrow_down);
        ImageButton left = this.findViewById(R.id.arrow_left);
        ImageButton right = this.findViewById(R.id.arrow_right);
        thread = (MainActivity.ThreadConnected)((App) getApplicationContext()).getThreadByName("bluetooth");

        setResult(RESULT_OK);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thread!=null)
                {
                    thread.write("u".getBytes());
                }
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thread!=null)
                {
                    thread.write("d".getBytes());
                }
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thread!=null)
                {
                    thread.write("l".getBytes());
                }
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thread!=null)
                {
                    thread.write("r".getBytes());
                }
            }
        });
    }
}
