package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.example.myfirstapp.adapters.StockListAdapters;
import com.example.myfirstapp.beens.stock_item;
import com.example.myfirstapp.sections.DateSection;
import com.example.myfirstapp.sections.PortBSection;
import com.example.myfirstapp.sections.PortHSection;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private RecyclerView list;
    private List<stock_item> datalist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find view list
        list =(RecyclerView) this.findViewById(R.id.stock_list);
        //prepare data
//        initStockData();
        initStockData_Section();
    }

    private void initStockData_Section() {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        for(int i=0;i<3;i++){
            if((i%3) ==0){
                stock_item date = new stock_item();
                date.type = 0;
                date.date = i+ " March, 2022";
                sectionAdapter.addSection(new DateSection(null,date));
            }
            else if((i%3) ==1){
                stock_item title = new stock_item();
                title.title="portfolio";
                stock_item porth = new stock_item();
                porth.real_time_value = 178.32;
                porth.current_money = 25000.00;
                List<stock_item> porth_list = new ArrayList<>();
                porth_list.add(porth);
                sectionAdapter.addSection(new PortHSection(null,title,porth_list));
            }
            else if(i%3 == 2){
                List<stock_item> portb_list= new ArrayList<>();
                String []acc_list = {"AAPL","TSLA","VMW"};
                for(int j=0;j<3;j++){
                    stock_item portb_item = new stock_item();
                    portb_item.ticker = acc_list[j];
                    portb_item.shares_or_name = (j+1) + " shares";
                    portb_item.closed_price = 123.45;
                    portb_item.change = 6.78;
                    portb_item.change_rate = 9.99;
                    portb_list.add(portb_item);
                }
                sectionAdapter.addSection(new PortBSection(null,portb_list));
            }
        }
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(sectionAdapter);
    }

    private void initStockData(){
        datalist = new ArrayList<>();
        for(int i=0;i<10;i++){
            // init data
            stock_item data = new stock_item();
            data.type = i%4;
            data.ticker = "TSLA";
            data.closed_price = 170.81;
            data.change = 0.01;
            data.change_rate = 0.01;
            data.shares_or_name = "3 shares";

            datalist.add(data);
        }
        //set style of RecycleView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        //create adapter
        StockListAdapters adapter = new StockListAdapters(datalist);
        //set adapter in the RecyclerView
        list.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return  super.onCreateOptionsMenu(menu);
    }

    public void sendMessage(View view){
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
    }


}