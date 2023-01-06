package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonStart, buttonStop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.id_button_start);
        buttonStop = findViewById(R.id.id_button_stop);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
            }
        });
    }

    void start() {
        Toast.makeText(this, "Start ne`", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MyBackgroundService.class);
        intent.setAction(MyBackgroundService.ACTION_START_SERVICE);
        ContextCompat.startForegroundService(this, intent);
    }

    void stop() {
        Toast.makeText(this, "Stop !!!`", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MyBackgroundService.class);
//        stopService(intent);
        intent.setAction(MyBackgroundService.ACTION_STOP_SERVICE);
        ContextCompat.startForegroundService(this, intent);
    }

}