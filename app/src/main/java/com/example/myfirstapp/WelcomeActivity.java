package com.example.myfirstapp;

import static com.example.myfirstapp.MainActivity.editor;
import static com.example.myfirstapp.MainActivity.sharedPreferences;
import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
//        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.super.getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        },2000);




//        final Thread thread = new Thread(new Runnable(){
//            @Override
//            public void run(){
//                try{
//                    sleep(1200);
//                    Intent intent = new Intent(WelcomeActivity.super.getApplicationContext(), Spinner_main.class);
//                    startActivity(intent);
//                    finish();
//                }
//                catch(InterruptedException e){
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();

    }
}