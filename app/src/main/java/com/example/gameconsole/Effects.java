package com.example.gameconsole;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class Effects extends AppCompatActivity {
    private BluetoothSocket btSocket;
    private Button effect1,effect2,effect3,effect4,effect5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effects);

        effect1=this.findViewById(R.id.effect1);
        effect2=this.findViewById(R.id.effect2);
        effect3=this.findViewById(R.id.effect3);
        effect4=this.findViewById(R.id.effect4);
        effect5=this.findViewById(R.id.effect5);
        btSocket = ((App)getApplication()).getBtSocket();

        effect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg("m",effect1);
            }
        });
        effect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg("c",effect2);
            }
        });
        effect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg("w",effect3);
            }
        });
        effect4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg("r",effect4);
            }
        });
        effect5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg("s",effect5);
            }
        });
    }

    void msg(String text,Button button){
        Toast.makeText(getApplicationContext(),"Playing effect: "+button.getText(),Toast.LENGTH_SHORT).show();
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write(text.getBytes());
            }
            catch (IOException e)
            {
                ((App)getApplication()).msg("Error");
            }
        }
    }
}
