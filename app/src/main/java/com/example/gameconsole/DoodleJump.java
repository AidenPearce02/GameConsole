package com.example.gameconsole;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class DoodleJump extends AppCompatActivity {
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
        setContentView(R.layout.activity_doodle_jump);

        mApp=(App)this.getApplicationContext();
        ImageButton up = this.findViewById(R.id.arrow_up_dj);
        TextView dj_win=this.findViewById(R.id.win_dj);
        up.setVisibility(View.VISIBLE);
        dj_win.setVisibility(View.GONE);
        thread = (MainActivity.ThreadConnected)((App) getApplicationContext()).getThread("bluetooth");

        setResult(RESULT_OK);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(thread!=null){
                    thread.write("w".getBytes());
                }
            }
        });
    }
}
