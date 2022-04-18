package com.example.myfirstapp;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    sleep(1500);
                    finish();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }
}