package com.example.gameconsole;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class Snake extends AppCompatActivity {
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
        setContentView(R.layout.activity_snake);

        ImageButton up = this.findViewById(R.id.arrow_up_snake);
        ImageButton down = this.findViewById(R.id.arrow_down_snake);
        ImageButton left = this.findViewById(R.id.arrow_left_snake);
        ImageButton right = this.findViewById(R.id.arrow_right_snake);
        TextView win_snake = this.findViewById(R.id.win_snake);
        up.setVisibility(View.VISIBLE);
        down.setVisibility(View.VISIBLE);
        left.setVisibility(View.VISIBLE);
        right.setVisibility(View.VISIBLE);
        win_snake.setVisibility(View.GONE);

        thread = (MainActivity.ThreadConnected)((App) getApplication()).getThread();
        mApp=(App)this.getApplicationContext();

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
