package com.example.gameconsole;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class Levels extends AppCompatActivity {
    private BluetoothSocket btSocket;
    private Button speed1,speed2,speed3,speed4,speed5,speed6,speed7,speed8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        speed1 = this.findViewById(R.id.speed1);
        speed2 = this.findViewById(R.id.speed2);
        speed3 = this.findViewById(R.id.speed3);
        speed4 = this.findViewById(R.id.speed4);
        speed5 = this.findViewById(R.id.speed5);
        speed6 = this.findViewById(R.id.speed6);
        speed7 = this.findViewById(R.id.speed7);
        speed8 = this.findViewById(R.id.speed8);
        btSocket=((App)getApplication()).getBtSocket();

        speed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg(1,speed1);
            }
        });
        speed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg(2,speed2);
            }
        });
        speed3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg(3,speed3);
            }
        });
        speed4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg(4,speed4);
            }
        });
        speed5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg(5,speed5);
            }
        });
        speed6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg(6,speed6);
            }
        });
        speed7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg(7,speed7);
            }
        });
        speed8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg(8,speed8);
            }
        });
    }

    void msg(int text, Button button){
        Toast.makeText(getApplicationContext(),button.getText(),Toast.LENGTH_SHORT).show();
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write(text);
            }
            catch (IOException e)
            {
                ((App)getApplication()).msg("Error");
            }
        }
        Intent i = new Intent();
        i.putExtra(getResources().getString(R.string.speedNow), button.getText());
        setResult(RESULT_OK,i);
        finish();
    }
}
