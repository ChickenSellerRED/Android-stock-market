package com.example.myfirstapp;

import static android.os.SystemClock.sleep;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.myfirstapp.adapters.PortHListAdapter;
import com.example.myfirstapp.adapters.StockListAdapters1;
import com.example.myfirstapp.beens.stock_item;
import com.example.myfirstapp.sections.DateSection;
import com.example.myfirstapp.sections.FavSection;
import com.example.myfirstapp.sections.PortBSection;
import com.example.myfirstapp.sections.PortHSection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String SEARCHED_TICKER = "com.example.myfirstapp.SEARCHED_TICKER";

    //    String backend = "https://stock-search-8-3.wm.r.appspot.com";
//    public static final String backend = "http://10.0.2.2:3080";
    public static final String backend = "https://stock-search-8-3.wm.r.appspot.com/";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    private RecyclerView list;
    private List<stock_item> datalist;

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static final DecimalFormat df = new DecimalFormat("0.00");

    public static final String []month_list = {"January","February","March","April","May","June","July","August","September","October","November","December"};

    int flag = 0;
    RequestQueue q;
    TextView finnhub;
    int ports_cnt = 0;
    int fav_cnt = 0;
    private Handler handler;

    SectionedRecyclerViewAdapter sectionAdapter;
    PortBSection portSection;
    PortHSection portHSection;
    DateSection dateSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onresume_flag = 1;
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean("main_pause",false);
        editor.apply();

        handler = new Handler();
        stock_item title = new stock_item();
        title.title = "FAVORITES";

        favSection = new FavSection(MainActivity.super.getApplicationContext(),null,title,new ArrayList<>());
        portHSection = new PortHSection(this,null,null,new ArrayList<>());
        portSection = new PortBSection(MainActivity.super.getApplicationContext(),null,new ArrayList<>());
        finnhub = this.findViewById(R.id.finnhub_info_text);
        finnhub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://finnhub.io/"));
                startActivity(browserIntent);
//                    browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        });
        q =  Volley.newRequestQueue(this);
        Context context = this;



        try {
            initPortfolio();
            initFavorites();
            editor.putBoolean("main_page_over",false);
            editor.apply();
            Intent intent = new Intent(this, Spinner_main.class);
            startActivity(intent);
            //find view list
            list =(RecyclerView) this.findViewById(R.id.stock_list);
            //prepare data
            initStockData_Section();


        } catch (JSONException e) {
            e.printStackTrace();
        }

//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                new Timer().scheduleAtFixedRate(new TimerTask(){
//                    @Override
//                    public void run(){
//                        //定时更新
//                        update_15s();
//                    }
//                },15000,15000);
//            }
//        });
        startRepeatingTask();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                 //this function can change value of mInterval.
                update_15s();
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                handler.postDelayed(mStatusChecker, 15000);
            }
        }
    };

    void startRepeatingTask() {
        handler.postDelayed(mStatusChecker, 15000);
//        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        handler.removeCallbacks(mStatusChecker);
    }

    public void initPortfolio() throws JSONException {
////      测试部分
//        editor.putFloat("net_worth",0);
//        editor.putFloat("money",25000);
//        String[] tem = {"TSLA","AAPL"};
////        System.out.println("init port:");
////        System.out.println(StringArray2String(tem));
////        String[] temp = String2StringArray(StringArray2String(tem));
////        for(int i=0;i<temp.length;i++)
////            System.out.print(temp[i]+", ");
//        editor.putString("p_ticker_names",StringArray2String(tem));
//        for(int i=0;i<tem.length;i++){
//            //shares
//            editor.putInt("p_"+tem[i]+"_s",1+i);
//            //avg price
//            editor.putFloat("p_"+tem[i]+"_a",(float)30.1);
//            //total price
//            editor.putFloat("p_"+tem[i]+"_t",(float) 30.1*(i+1));
//        }
//        editor.apply();


//        editor.clear();
//        editor.apply();
//        if(sharedPreferences.getFloat("net_worth",-1) == -1)
//            editor.putFloat("net_worth",25000);
//        if(sharedPreferences.getFloat("money",-1) == -1)
//            editor.putFloat("money",25000);
//        editor.apply();
        portList = String2StringArray(sharedPreferences.getString("p_ticker_names",""));
    }
    public static String []favList;
    public static String []portList;
    public void initFavorites() throws JSONException{

//        String[] tem = {"TSLA","AAPL"};
//        editor.putString("f_ticker_names",StringArray2String(tem));
//        editor.apply();
        favList = String2StringArray(sharedPreferences.getString("f_ticker_names",""));
    }



    private void updateDateInfo(){
        //日期
        Date date1 = new Date(getNowUnix() * 1000L);
        String now_date = date1.getDate() + " " + month_list[date1.getMonth()] + " "  + (date1.getYear()+1900);
        stock_item date = new stock_item();
        date.type = 0;
        date.date = now_date;
        dateSection = new DateSection(null,date);
    }


    private void initStockData_Section() throws JSONException {

        RequestQueue q = Volley.newRequestQueue(this);
        sectionAdapter = new SectionedRecyclerViewAdapter();


        //日期
        Date date1 = new Date(getNowUnix() * 1000L);
        String now_date = date1.getDate() + " " + month_list[date1.getMonth()] + " "  + (date1.getYear()+1900);
        stock_item date = new stock_item();
        date.type = 0;
        date.date = now_date;
        dateSection = new DateSection(null,date);
        sectionAdapter.addSection(dateSection);


        //账户信息
        stock_item title = new stock_item();
        title.title="PORTFOLIO";
        stock_item porth = new stock_item();
//        porth.real_time_value = (double)sharedPreferences.getFloat("net_worth",0);
        porth.real_time_value = (double)sharedPreferences.getFloat("money",0);
        porth.current_money = (double)sharedPreferences.getFloat("money",0);
        List<stock_item> porth_list = new ArrayList<>();
        porth_list.add(porth);
        portHSection = new PortHSection(this,null,title,porth_list);
        sectionAdapter.addSection(portHSection);

        //                钱包里的股票
        ArrayList<stock_item> portb_list= new ArrayList<>();
        String[] ports = String2StringArray(sharedPreferences.getString("p_ticker_names",""));
//                double[] priceList = getPortPriceList(ports);
//                System.out.println("pricelist:"+ priceList.length);
        ports_cnt = 0;
        if(ports.length == 0){
            portSection = new PortBSection(MainActivity.super.getApplicationContext(),null,portb_list);
            sectionAdapter.addSection(portSection);
//            portHSection.list.get(0).real_time_value = portHSection.list.get(0).current_money;
            setFavStocks();
        }
        else
            for(int j=0;j<ports.length;j++){
                System.out.println("ports["+ j + "] = "+ports[j]);
                String cur_ticker = ports[j];
                int shares = sharedPreferences.getInt("p_"+cur_ticker+"_s",-1);
                double avg_cost = sharedPreferences.getFloat("p_"+cur_ticker+"_a",-1);
                double total_cost = sharedPreferences.getFloat("p_"+cur_ticker+"_t",-1);
                stock_item portb_item = new stock_item();
                portb_item.ticker = cur_ticker;
                portb_item.shares_or_name = shares + " shares";
                String url = String.format(backend + "/get_4p3?ticker=%1$s",ports[j]);
                System.out.println("url "+url);
                StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject result = new JSONObject(response);
                                    System.out.println("closed price: " + result.get("c"));
                                    double closed_price = (Double)result.get("c");
                                    System.out.println("response: " + response);
//                                    System.out.println("closed price: " + closed_price);
                                    portb_item.closed_price = closed_price * shares;
                                    portb_item.change = (closed_price - avg_cost) * shares;
                                    portb_item.change_rate = (portb_item.change / total_cost)*shares;
                                    portb_item.avg_cost = avg_cost;
                                    portb_list.add(portb_item);
                                    ports_cnt ++;
                                    portHSection.list.get(0).real_time_value += portb_item.closed_price;
                                    if(ports_cnt==ports.length){
                                        portSection = new PortBSection(MainActivity.super.getApplicationContext(),null,portb_list);
                                        sectionAdapter.addSection(portSection);
                                        setFavStocks();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("port didn't work!");
                    }
                });
                q.add(stringRequest);
            }
//              关注的股票
    }
    FavSection favSection;
    private void setFavStocks(){
//        RequestQueue q = Volley.newRequestQueue(this);

        ArrayList<stock_item> portb_list= new ArrayList<>();
        if(favList.length == 0){
            stock_item title = new stock_item();
            title.title = "FAVORITES";
            favSection = new FavSection(MainActivity.super.getApplicationContext(),null,title,portb_list);
            sectionAdapter.addSection(favSection);
            list.setLayoutManager(new LinearLayoutManager(MainActivity.super.getApplicationContext()));
            list.setAdapter(sectionAdapter);
            onresume_flag = 2;
            editor.putBoolean("main_page_over",true);
            editor.apply();
//            sleep(2000);
//            updateWallet();

            return;
        }
        for(int j=0;j<favList.length;j++){
            System.out.println("favs["+ j + "] = "+favList[j]);
            stock_item portb_item = new stock_item();
            portb_item.ticker = favList[j];

            //get price
            String url = String.format(backend + "/get_4p3andname?ticker=%1$s",favList[j]);
            System.out.println("url "+url);
            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject result = new JSONObject(response);
                                portb_item.closed_price = (Double) result.get("c");
                                portb_item.change = (Double) result.get("d") ;
                                portb_item.change_rate = (Double) result.get("dp");
                                portb_item.shares_or_name = (String)result.get("name");
                                portb_list.add(portb_item);
                                fav_cnt ++;
                                if(fav_cnt == favList.length){
                                    stock_item title = new stock_item();
                                    title.title = "FAVORITES";
                                    favSection = new FavSection(MainActivity.super.getApplicationContext(),null,title,portb_list);
                                    sectionAdapter.addSection(favSection);
                                    list.setLayoutManager(new LinearLayoutManager(MainActivity.super.getApplicationContext()));
                                    list.setAdapter(sectionAdapter);
//                                    updateWallet();
                                    onresume_flag = 2;

                                    editor.putBoolean("main_page_over",true);
                                    editor.apply();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("fav didn't work!");
                }
            });
            q.add(stringRequest);
        }
    }
    int onresume_flag = 0;

    private void update_15s(){
        if(sharedPreferences.getBoolean("main_pause",true))
            return;
        System.out.println("15s 固定更新");
//        // 更新wallet钱数
//        updateWallet();
        //更新钱包股票
        updatePortStocks();
        // 更新关注列表
        updateFavStocks();
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
        StockListAdapters1 adapter = new StockListAdapters1(datalist);
        //set adapter in the RecyclerView
        list.setAdapter(adapter);
    }

    String []dataArr ;
    static int attemp_to_autocomplete_flag = 0;
    ArrayAdapter<String> newsAdapter;
    SearchView.SearchAutoComplete searchAutoComplete;
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_with_searchbar,menu);

        MenuItem MenuWithSearchbar = menu.findItem(R.id.action_search);
        try {
            SearchView searchView = (SearchView) MenuWithSearchbar.getActionView();
            searchView.setQueryHint("Type here");
//            androidx.appcompat.widget.SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
//            androidx.appcompat.widget.SearchView.SearchAutoComplete searchAutoComplete
//            List<String> ticker = new ArrayList<>();
//            List<String> name = new ArrayList<>();
//            ticker.add("AAPL");ticker.add("TSLA");
//            name.add("APPLE");name.add("TESILA");
//            AutoCompleteAdapter autoCompleteAdapter = new AutoCompleteAdapter(ticker,name);
            // Get SearchView autocomplete object.
            searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(androidx.appcompat.R.id.search_src_text);
//            searchAutoComplete.setBackgroundColor(Color.BLUE);
//            searchAutoComplete.setTextColor(Color.GREEN);
//            searchAutoComplete.setDropDownBackgroundResource(android.R.color.holo_blue_light);

            // Create a new ArrayAdapter and add data to search auto complete object.
            dataArr = new String[]{"Apple" , "Amazon" , "Amd", "Microsoft|123", "Microwave", "MicroNews", "Intel", "Intelligence"};
            newsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, dataArr);
            searchAutoComplete.setAdapter(newsAdapter);
            dataArr[3] = "Microsoft|1234";
            newsAdapter.notifyDataSetChanged();
//            searchAutoComplete.notify();



            // Listen to search view item on click event.
            searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                    String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                    String cur_ticker = "" + queryString.split(" | ")[0];
                    searchAutoComplete.setText(cur_ticker);
                    Intent detail = new Intent(MainActivity.super.getApplicationContext(),DetailStockActivity.class);
                    detail.putExtra(SEARCHED_TICKER, cur_ticker);
                    startActivity(detail);
                }
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {

                    searchView.clearFocus();
                    /// TODO: 验证
                    String url = String.format(backend + "/verify?ticker=%1$s",s);
                    System.out.println("url "+url);
                    StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET,
                            url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject result = new JSONObject(response);
                                        if(result.getBoolean("verify")){
                                            Intent detail = new Intent(MainActivity.super.getApplicationContext(),DetailStockActivity.class);
                                            detail.putExtra(SEARCHED_TICKER, s);
                                            startActivity(detail);
                                        }else{
                                            Toast.makeText(MainActivity.super.getApplicationContext(), "invalid stock symbol!",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("fav didn't work!");
                        }
                    });

                    RequestQueue q = Volley.newRequestQueue(MainActivity.super.getApplicationContext());
                    q.add(stringRequest);


                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    //自动补全
                    String cur_ticker = s;
                    System.out.println("dataArr 0= " + dataArr[0]);
                    System.out.println("cur_ticker= " + cur_ticker);
                    if(cur_ticker.equals(""))
                        return false;
                    int tem = attemp_to_autocomplete_flag+1;
                    attemp_to_autocomplete_flag++;
                    get_autocomplete(300,tem,cur_ticker);
//                    update_autocomplete(cur_ticker);
                    return false;
                }
            });
        }catch (Exception e){
            System.out.println("error"+e);
        }

        return  super.onCreateOptionsMenu(menu);
    }

    void update_autocomplete(String cur_ticker){
        RequestQueue q = Volley.newRequestQueue(MainActivity.super.getApplicationContext());
        String url = String.format(backend + "/get_auto_complete?cur_ticker=%1$s",cur_ticker);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject result = new JSONObject(response);
                            JSONArray auto_list= result.getJSONArray("result");
                            dataArr = new String[auto_list.length()];

                            for(int i=0;i<auto_list.length();i++){
                                dataArr[i] = auto_list.getJSONObject(i).getString("symbol")
                                        + " | " +
                                        auto_list.getJSONObject(i).getString("description");
                                System.out.println("dataArr " + i + " =" + dataArr[i]);
                            }
                            System.out.println("on response");
                            newsAdapter = new ArrayAdapter<String>(MainActivity.super.getApplicationContext(), android.R.layout.simple_dropdown_item_1line, dataArr);
                            searchAutoComplete.setAdapter(newsAdapter);
                            newsAdapter.setNotifyOnChange(true);
                            newsAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("autocomplete didn't work!");
            }
        });
        q.add(stringRequest);



    }

    int f_tobeadd_cnt;
    int p_tobeadd_cnt;
    @Override
    protected void onResume() {

        super.onResume();
        if(onresume_flag < 3){

            System.out.println("onResume 失败!");
            onresume_flag ++;
            return ;
        }
        editor.putBoolean("main_pause",false);
        editor.apply();
        System.out.println("onResume 成功!");
//        updateWallet();
        updatePortStocks();
        updateFavStocks();
    }
    double net_worth;
    int new_worth_cnt;
//    RequestQueue q = Volley.newRequestQueue(this);
    private void updateWallet(){
        String[] ports = String2StringArray(sharedPreferences.getString("p_ticker_names",""));
        int n = ports.length;
        net_worth = 0;
        new_worth_cnt = n;
        if(n == 0){
            double money = sharedPreferences.getFloat("money",0);
            portHSection.list.get(0).current_money = money;
            net_worth += money;
            portHSection.list.get(0).real_time_value = net_worth;                                    try {
                portHSection.portHListAdapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }
            return;
        }
        for(int i=0;i<n;i++){
            String cur_ticker = ports[i];
            String url = String.format(backend + "/get_4p3andname?ticker=%1$s",cur_ticker);
            System.out.println("url "+url);
            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject result = new JSONObject(response);
                                int share = sharedPreferences.getInt("p_"+cur_ticker+"_s",0);
                                net_worth+= share * result.getDouble("c");
                                new_worth_cnt --;
                                if(new_worth_cnt == 0){
                                    double money = sharedPreferences.getFloat("money",0);
                                    portHSection.list.get(0).current_money = money;
                                    net_worth += money;
                                    portHSection.list.get(0).real_time_value = net_worth;
//                                    sectionAdapter.notifyDataSetChanged();
//                                    if(portHSection.portHListAdapter == null)
//                                        portHSection.portHListAdapter = new PortHListAdapter(portHSection.list,portHSection.context);
                                    try {
                                        portHSection.portHListAdapter.notifyDataSetChanged();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("updateWallet didn't work!");
                }
            });
            q.add(stringRequest);
        }


    }
    private void updateFavStocks() {
        RequestQueue q = Volley.newRequestQueue(this);
        String[] favs = String2StringArray(sharedPreferences.getString("f_ticker_names",""));
        System.out.println("main favs: " + StringArray2String(favs));
        System.out.println("main favList: " + StringArray2String(favList));
//        if(isFavArraySame(favList,favs))
//            return;
        favList = favs;
        fav_cnt = favList.length;
        ArrayList<String> fav_order = new ArrayList<>();
        favSection.list.forEach((item) ->{
            fav_order.add(item.ticker);
        });
        if(favSection.list.size() > 0 )
            favSection.list.clear();
        if(favList.length == 0){
//            //更新钱包里的股票
//            updatePortStocks();
            favSection.stockListAdapter.notifyDataSetChanged();
            System.out.println("fav notifyDataSetChanged0!");
            return;
        }
        for(int i=0;i<favList.length;i++){
            String ticker = favList[i];
            String url = String.format(backend + "/get_4p3andname?ticker=%1$s",ticker);
            System.out.println("url "+url);
            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject result = new JSONObject(response);
                                stock_item item = new stock_item();
                                item.ticker = ticker;
                                item.closed_price = (Double) result.get("c");
                                item.change = (Double) result.get("d") ;
                                item.change_rate = (Double) result.get("dp");
                                item.shares_or_name = (String)result.get("name");
                                favSection.list.add(item);
                                fav_cnt --;
                                if(fav_cnt == 0){
                                    if(isArrayListSame(fav_order,favSection.list)){
                                        favSection.list = adjustItemListOrder(fav_order,favSection.list);
                                    }
//                                    sectionAdapter.notifyDataSetChanged();
//                                    portSection.stockListAdapter.notifyDataSetChanged();
                                    favSection.stockListAdapter.notifyDataSetChanged();
                                    System.out.println("fav notifyDataSetChanged1!");

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("fav didn't work!");
                }
            });
            q.add(stringRequest);
        }
    }


    private void updatePortStocks(){
        RequestQueue q = Volley.newRequestQueue(this);
        String[] ports = String2StringArray(sharedPreferences.getString("p_ticker_names",""));
        if(portSection == null)
            return;

        ArrayList<String> ports_order = new ArrayList<>();
        favSection.list.forEach((item) ->{
            ports_order.add(item.ticker);
        });

        if(portSection.list.size() > 0 )
            portSection.list.clear();
        portList = ports;
        ports_cnt = portList.length;
        if(ports_cnt == 0){
            try{
                portSection.updateData();
//                .stockListAdapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }
            updateWallet();
        }
        for(int i=0;i<portList.length;i++){
            String ticker = portList[i];
            int shares = sharedPreferences.getInt("p_"+ticker+"_s",0);
            double avg_cost = sharedPreferences.getFloat("p_"+ticker+"_a",-1);
            double total_cost = sharedPreferences.getFloat("p_"+ticker+"_t",-1);
            String url = String.format(backend + "/get_4p3andname?ticker=%1$s",ticker);
            System.out.println("url "+url);
            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject result = new JSONObject(response);
                                double closed_price = (Double)result.get("c");
                                System.out.println("response: " + response);
                                System.out.println("closed price: " + closed_price);
                                stock_item item = new stock_item();
                                item.ticker = ticker;
                                item.shares_or_name = shares + " shares";
                                item.closed_price = closed_price * shares;
                                item.change = (closed_price - avg_cost) * shares;
                                item.change_rate = (item.change / total_cost)*shares;
                                item.avg_cost = avg_cost;
                                portSection.list.add(item);

                                ports_cnt --;
                                if(ports_cnt == 0){
//                                    sectionAdapter.notifyDataSetChanged();
                                    if(isArrayListSame(ports_order,portSection.list)){
                                        portSection.list = adjustItemListOrder(ports_order,portSection.list);
                                    }
                                    portSection.stockListAdapter.notifyDataSetChanged();
                                    updateWallet();
//                                    //更新钱包里的股票
//                                    updatePortStocks();

//                                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(portSection.simpleCallback);
//                                    itemTouchHelper.attachToRecyclerView(portSection.recyclerView);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("updatePortStocks didn't work!");
                }
            });
            q.add(stringRequest);
        }
    }

    public void sendMessage(View view){
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
    }

    public double[] getPortPriceList(String []ports) {

        RequestQueue q = Volley.newRequestQueue(super.getApplicationContext());

        HashMap<String,Integer> tem = new HashMap<>();
        double []ans = new double[ports.length];

        for(int i=0;i<ports.length;i++){
            tem.put(ports[i],i);
            String url = String.format(backend + "/get_4p3?ticker=%1$s",ports[i]);
            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject result = new JSONObject(response);
                                String ticker = url.split("=")[1];
                                ans[tem.get(ticker)] = (double)result.get("c");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("didn't work!");
                }
            });
            q.add(stringRequest);
        }
        return ans;
    }

    public static final String StringArray2String(String[] names){
        String ans = "";
        for(int i=0;i< names.length;i++)
            if(i == names.length)
                ans += names[i];
            else
                ans += names[i] + ", ";
        return ans;
    }
    public static final String[] String2StringArray(String names){
        if(names.equals(""))
            return new String[0];
        String[] ans = names.split(", ");
        return ans;
    }
    public static final boolean checkContains(String[] l, String s){
        for(int i=0;i<l.length;i++){
            if(l[i].equals(s))
                return true;
        }
        return false;
    }
    public static final String[] removeString(String[] l, String s){
        ArrayList<String> tem = new ArrayList<>(Arrays.asList(l));
        if(tem.contains(s)){
            tem.remove(tem.indexOf(s));
        }
        String []ans = new String[tem.size()];
        for(int i=0;i<tem.size();i++)
            ans[i] = tem.get(i);
        return ans;
    }
    //判断关注列表是否被更新
    public static final boolean isFavArraySame(String[] a, String[] b){
        if(a.length != b.length)
            return false;
        ArrayList<String> aL= new ArrayList<>(Arrays.asList(a));
        ArrayList<String> bL= new ArrayList<>(Arrays.asList(b));
        for(int i=0;i<a.length;i++){
            if(!aL.contains(bL.get(i)))
                return false;
        }
        return true;
    }

    //判断持有的股票是否被更新
    public static final boolean isPortArraySame(String[] a, String[] b){
        if(a.length != b.length)
            return false;
        ArrayList<String> aL= new ArrayList<>(Arrays.asList(a));
        ArrayList<String> bL= new ArrayList<>(Arrays.asList(b));
        for(int i=0;i<a.length;i++){
            if(!aL.contains(bL.get(i)))
                return false;
        }
        return true;
    }
    public static boolean isArrayListSame(ArrayList<String> a, ArrayList<stock_item> b){

//        System.out.println("a,b issame:");
//        for(int i=0;i<b.size();i++)
//            System.out.print(a.get(i) + ", ");
//        System.out.println();
//        for(int i=0;i<b.size();i++)
//            System.out.print(b.get(i) + ", ");
        if(a.size() != b.size())
            return false;
        for(int i=0;i<b.size();i++){
            if(!a.contains(b.get(i).ticker))
                return false;
        }
        return true;
    }

    public static ArrayList<stock_item> adjustItemListOrder(ArrayList<String> a, ArrayList<stock_item> b){

//        System.out.println("a,b:");
//        for(int i=0;i<b.size();i++)
//            System.out.print(a.get(i) + ", ");
//        System.out.println();
//        for(int i=0;i<b.size();i++)
//            System.out.print(b.get(i).ticker + ", ");
        for(int i=0;i<b.size();i++){
            String cur_s = b.get(i).ticker;
            int index = a.indexOf(cur_s);
            while(i!=index){
                Collections.swap(b,i,index);
                index = a.indexOf(b.get(i).ticker);
            }
        }
        return b;
    }
    //获取现在时间戳
    public static final long getNowUnix(){

        long nowTime = Instant.now().getEpochSecond();
        return nowTime;
    }

    public void get_autocomplete( int delay,int tem, String cur_ticker){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                if(tem == MainActivity.attemp_to_autocomplete_flag){
                    update_autocomplete(cur_ticker);
                }
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }
}