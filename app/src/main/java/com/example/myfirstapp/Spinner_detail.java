package com.example.myfirstapp;

import static com.example.myfirstapp.MainActivity.sharedPreferences;
import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Spinner_detail  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_main);

        final Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
//                try {
//                    sleep(1500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                while(true){
                    boolean detail_page_over = sharedPreferences.getBoolean("detail_page_over",false);
//                    boolean chart1 = sharedPreferences.getBoolean("chart_1",false);
//                    boolean chart2 = sharedPreferences.getBoolean("chart_2",false);
//                    boolean chart3 = sharedPreferences.getBoolean("chart_3",false);
//                    boolean chart4 = sharedPreferences.getBoolean("chart_4",false);
//                    System.out.println(chart1 + " " + chart2 + " " + chart3 + " " + chart4);
//                    if(chart1 && chart2 && chart3 && chart4)
//                        break;
                    if(detail_page_over)
                        break;
                }
                finish();
            }
        });
        thread.start();
    }
}