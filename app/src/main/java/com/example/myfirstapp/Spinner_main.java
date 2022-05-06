package com.example.myfirstapp;

import static com.example.myfirstapp.MainActivity.sharedPreferences;
import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Spinner_main  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_main);

        final Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while(sharedPreferences.getBoolean("main_page_over",false) == false){}
                finish();
            }
        });
        thread.start();
    }
}