package com.example.myfirstapp;

import static com.example.myfirstapp.MainActivity.String2StringArray;
import static com.example.myfirstapp.MainActivity.StringArray2String;
import static com.example.myfirstapp.MainActivity.backend;
import static com.example.myfirstapp.MainActivity.checkContains;
import static com.example.myfirstapp.MainActivity.removeString;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfirstapp.adapters.DetailStockAdapter;
import com.example.myfirstapp.beens.detail_about;
import com.example.myfirstapp.beens.detail_headline;
import com.example.myfirstapp.beens.detail_insights;
import com.example.myfirstapp.beens.detail_news;
import com.example.myfirstapp.beens.detail_portfolio;
import com.example.myfirstapp.beens.detail_stats;
import com.example.myfirstapp.beens.detail_stock_item;
import com.example.myfirstapp.beens.detail_chartone;
import com.example.myfirstapp.sections.PortBSection;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DetailStockActivity extends AppCompatActivity {

    private RecyclerView detail_page_view;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    private boolean update_flag = false;
    private JSONObject ticker_data = new JSONObject();
    private String ticker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean("main_pause",true);
        editor.apply();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_stock);

        editor.putBoolean("detail_page_over",false);
        editor.apply();
        Intent intent1 = new Intent(this, Spinner_detail.class);
        startActivity(intent1);



        //设置up键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        ticker = intent.getStringExtra(MainActivity.SEARCHED_TICKER);
        setTitle(ticker);

        detail_page_view = this.findViewById(R.id.detail_page_view);
        detail_page_view.setLayoutManager(new LinearLayoutManager(this));

        // get stock data
        //10位 unix
        long nowTime = Instant.now().getEpochSecond();
        long aWeekAgo = nowTime - 7 * 24 * 3600;
        Date date = new Date(nowTime*1000L);
        // 设置显示格式化
        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd");
        //设置时区
        jdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String nowTimeString = jdf.format(new Date(nowTime*1000L));
        String aWeekAgoString = jdf.format(new Date(aWeekAgo*1000L));

        RequestQueue q = Volley.newRequestQueue(this);
        String url = String.format(backend + "/get_ticker_detail?ticker=%1$s&from_s=%2$s&from_n=%3$s&to_n=%4$s",
                ticker,
                "2022-01-01",
                aWeekAgoString,
                nowTimeString
                );
        System.out.println("detail url "+url);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject result = new JSONObject(response);
                            System.out.println("response: " + response);
                            System.out.println("result: " + result);
                            update_flag = true;
                            ticker_data = result;
                            updateFromBackend();
                            editor.putBoolean("detail_page_over",true);
                            editor.apply();
                            System.out.println("detail_page_over : true!");

                        } catch (JSONException e) {
                            System.out.println("error: "+e);
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("port didn't work!");
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        q.add(stringRequest);


    }
    List<detail_stock_item> detail_item_list;
    DetailStockAdapter detailStockAdapter;
    public void updateFromBackend() throws JSONException {
        String ticker = ticker_data.getJSONObject("4p1").getString("ticker");
        detail_item_list = new ArrayList<>();
        detail_headline item_headline = new detail_headline(ticker_data.getJSONObject("4p1").getString("logo"),
                ticker_data.getJSONObject("4p1").getString("ticker"),
                ticker_data.getJSONObject("4p1").getString("name"),
                ticker_data.getJSONObject("4p3").getDouble("c"),
                ticker_data.getJSONObject("4p3").getDouble("d"),
                ticker_data.getJSONObject("4p3").getDouble("dp"));

        detail_chartone item_chartone = new detail_chartone(ticker);

        double port_change=ticker_data.getJSONObject("4p3").getDouble("d");
        double port_mktvalue=0;
        if(sharedPreferences.getInt("p_"+ticker+"_s",-1) != -1) {
            port_mktvalue = (double) sharedPreferences.getInt("p_" + ticker + "_s", 0) * ticker_data.getJSONObject("4p3").getDouble("c");
        }
        detail_portfolio item_portfolio= new detail_portfolio(sharedPreferences.getInt("p_"+ticker+"_s",0),
            (double)sharedPreferences.getFloat("p_"+ticker+"_a",0),
            (double)sharedPreferences.getFloat("p_"+ticker+"_t",0),
            port_change,
            port_mktvalue,
            ticker_data.getJSONObject("4p3").getDouble("c"),
            (double)sharedPreferences.getFloat("money",(float)0.0),
            ticker,
            ticker_data.getJSONObject("4p1").getString("name"));
        detail_stats item_stats = new detail_stats(ticker_data.getJSONObject("4p3").getDouble("o"),
                ticker_data.getJSONObject("4p3").getDouble("h"),
                ticker_data.getJSONObject("4p3").getDouble("l"),
                ticker_data.getJSONObject("4p3").getDouble("pc"));
        ArrayList<String> peers = new ArrayList<>();

        //peers
        for(int i=0;i<ticker_data.getJSONArray("4p8").length();i++)
            peers.add((String)ticker_data.getJSONArray("4p8").get(i));
        detail_about item_about = new detail_about(ticker_data.getJSONObject("4p1").getString("ipo"),
                ticker_data.getJSONObject("4p1").getString("finnhubIndustry"),
                ticker_data.getJSONObject("4p1").getString("weburl"),
                peers);

        //calculate sentiment
        int t_r=0,t_t=0,p_r=0,p_t=0,n_r=0,n_t=0;
        for(int i=0;i<ticker_data.getJSONObject("4p7").getJSONArray("twitter").length();i++){
            t_t += ticker_data.getJSONObject("4p7").getJSONArray("twitter").getJSONObject(i).getInt("mention");
            p_t += ticker_data.getJSONObject("4p7").getJSONArray("twitter").getJSONObject(i).getInt("positiveMention");
            n_t += ticker_data.getJSONObject("4p7").getJSONArray("twitter").getJSONObject(i).getInt("negativeMention");
        }
        for(int i=0;i<ticker_data.getJSONObject("4p7").getJSONArray("reddit").length();i++){
            t_r += ticker_data.getJSONObject("4p7").getJSONArray("reddit").getJSONObject(i).getInt("mention");
            p_r += ticker_data.getJSONObject("4p7").getJSONArray("reddit").getJSONObject(i).getInt("positiveMention");
            n_r += ticker_data.getJSONObject("4p7").getJSONArray("reddit").getJSONObject(i).getInt("negativeMention");
        }
        detail_insights item_insights = new detail_insights(
                ticker_data.getJSONObject("4p1").getString("name"),
                t_r,
                t_t,
                p_r,
                p_t,
                n_r,
                n_t,
                ticker);

        detail_item_list.add(item_headline);
        detail_item_list.add(item_chartone);
        detail_item_list.add(item_portfolio);
        detail_item_list.add(item_stats);
        detail_item_list.add(item_about);
        detail_item_list.add(item_insights);

        //set news
        int news_cnt = 0;
        int news_cur = 0;
        while(news_cnt<20){
            if(news_cur >= ticker_data.getJSONArray("4p5").length())
                break;
            if(ticker_data.getJSONArray("4p5").getJSONObject(news_cur).getString("image").equals("")){
                news_cur++;
                continue;
            }
            detail_news item_news = new detail_news(ticker_data.getJSONArray("4p5").getJSONObject(news_cur).getString("source"),
                    ticker_data.getJSONArray("4p5").getJSONObject(news_cur).getInt("datetime"),
                    ticker_data.getJSONArray("4p5").getJSONObject(news_cur).getString("headline"),
                    ticker_data.getJSONArray("4p5").getJSONObject(news_cur).getString("image"),
                    ticker_data.getJSONArray("4p5").getJSONObject(news_cur).getString("summary"),
                    ticker_data.getJSONArray("4p5").getJSONObject(news_cur).getString("url"),
                    news_cnt==0?8:6
            );
            detail_item_list.add(item_news);
            news_cur++;
            news_cnt ++ ;

        }

        detailStockAdapter = new DetailStockAdapter(this,detail_item_list);
        detail_page_view.setAdapter(detailStockAdapter);
        detail_page_view.setItemViewCacheSize(30);
        detail_page_view.setAdapter(detailStockAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.detail_stock_menu,menu);
        MenuItem star = menu.findItem(R.id.ticker_follow);
        String [] followList = String2StringArray(sharedPreferences.getString("f_ticker_names",""));
        if(checkContains(followList,ticker) == true)
            star.setIcon(R.drawable.star);

        star.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //follow / unfollow

                String [] followList1 = String2StringArray(sharedPreferences.getString("f_ticker_names",""));
                if(checkContains(followList1,ticker) == true){
                    star.setIcon(R.drawable.star_outline);
                    followList1 = removeString(followList1,ticker);
                    System.out.println("star: " + StringArray2String(followList1));
                    editor.putString("f_ticker_names",StringArray2String(followList1));
                    editor.apply();
                    Toast.makeText(DetailStockActivity.super.getApplicationContext(), ticker + " is removed from favorites",
                            Toast.LENGTH_LONG).show();
                }else{
                    star.setIcon(R.drawable.star);
                    ArrayList<String> tem = new ArrayList<>(Arrays.asList(followList1));
                    tem.add(ticker);
                    followList1 = new String[tem.size()];
                    for(int i=0;i<tem.size();i++)
                        followList1[i] = tem.get(i);
                    editor.putString("f_ticker_names",StringArray2String(followList1));
                    editor.apply();
                    Toast.makeText(DetailStockActivity.super.getApplicationContext(), ticker + " is added to favorites",
                            Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
        return  super.onCreateOptionsMenu(menu);
    }

}