package com.example.gameconsole;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TypeOfSnakeMaze extends AppCompatActivity {
    private App mApp;
    private MainActivity.ThreadConnected thread;
    private Button level0,level1,level2,level3,level4,level5;

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
        setContentView(R.layout.activity_type_of_snake_maze);

        level0=this.findViewById(R.id.level0);
        level1=this.findViewById(R.id.level1);
        level2=this.findViewById(R.id.level2);
        level3=this.findViewById(R.id.level3);
        level4=this.findViewById(R.id.level4);
        level5=this.findViewById(R.id.level5);

        thread=(MainActivity.ThreadConnected)((App)getApplication()).getThread();
        mApp=(App)this.getApplicationContext();

        level0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg("0",level0);
            }
        });
        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg("1",level1);
            }
        });
        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg("2",level2);
            }
        });
        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg("3",level3);
            }
        });
        level4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg("4",level4);
            }
        });
        level5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg("5",level5);
            }
        });
    }

    void msg(String text,Button button){
        Toast.makeText(getApplicationContext(),button.getText(),Toast.LENGTH_SHORT).show();
        if (thread!=null)
        {
            thread.write(text.getBytes());
        }
        ((App)getApplicationContext()).setMazeNow(button.getText().toString());
        Intent i = new Intent();
        i.putExtra(getResources().getString(R.string.mazeNow), button.getText());
        setResult(RESULT_OK,i);
        finish();
    }
}
