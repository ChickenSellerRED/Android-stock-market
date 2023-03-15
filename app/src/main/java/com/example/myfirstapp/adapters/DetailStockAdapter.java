package com.example.myfirstapp.adapters;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.example.myfirstapp.MainActivity.SEARCHED_TICKER;
import static com.example.myfirstapp.MainActivity.String2StringArray;
import static com.example.myfirstapp.MainActivity.StringArray2String;
import static com.example.myfirstapp.MainActivity.df;
import static com.example.myfirstapp.MainActivity.editor;
import static com.example.myfirstapp.MainActivity.getNowUnix;
import static com.example.myfirstapp.MainActivity.removeString;
import static com.example.myfirstapp.MainActivity.sharedPreferences;

import androidx.annotation.NonNull;
import com.bumptech.glide.RequestBuilder;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.example.myfirstapp.DetailStockActivity;
//import com.example.myfirstapp.GlideApp;
import com.example.myfirstapp.R;
import com.example.myfirstapp.svg4glide.GlideApp;
import com.example.myfirstapp.svg4glide.SvgSoftwareLayerSetter;
import com.example.myfirstapp.beens.detail_about;
import com.example.myfirstapp.beens.detail_chartone;
import com.example.myfirstapp.beens.detail_insights;
import com.example.myfirstapp.beens.detail_news;
import com.example.myfirstapp.beens.detail_portfolio;
import com.example.myfirstapp.beens.detail_stats;
import com.example.myfirstapp.dialogs.TradeDialog;
import com.example.myfirstapp.fragments.RealTimeStock;
import com.example.myfirstapp.fragments.VbpStock;
import com.example.myfirstapp.beens.detail_headline;
import com.example.myfirstapp.beens.detail_stock_item;
import com.example.myfirstapp.webAppInterfaces.VBPWebAppInterface;
import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DetailStockAdapter extends RecyclerView.Adapter {

    final private Context context;
    final private Integer news_cnt = 0;
    final private List<detail_stock_item> items;
    public static final int TYPE_HEADLINE = 0;
    public static final int TYPE_CHARTONE = 1;
    public static final int TYPE_PORTFOLIO = 2;
    public static final int TYPE_STATS = 3;
    public static final int TYPE_ABOUT = 4;
    public static final int TYPE_INSIGHTS = 5;
    public static final int TYPE_NEWS = 6;
    public static final int TYPE_NEWSTITLE =7;
    public static final int TYPE_FIRST_NEWS =8;

    public DetailStockAdapter(Context context1,List<detail_stock_item> item_list) {
        this.items = item_list;
        this.context = context1;
    }


    @Override
    public int getItemViewType(int position) {
        detail_stock_item tem = items.get(position);
       return tem.type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case TYPE_HEADLINE:{
                view = View.inflate(parent.getContext(), R.layout.detail_headline,null);
                return new DetailHeadlineHolder(context, view);
            }
            case TYPE_CHARTONE:{
                view = View.inflate(parent.getContext(), R.layout.detail_chartone,null);
                return new DetailChartoneHolder(context, view);
            }
            case TYPE_PORTFOLIO:{
                view = View.inflate(parent.getContext(),R.layout.detail_portfolio,null);
                return new DetailPortfolioHolder(context,view);
            }
            case TYPE_STATS:{
                view = View.inflate(parent.getContext(),R.layout.detail_stats,null);
                return new DetailStatsHolder(context,view);
            }
            case TYPE_ABOUT:{
                view = View.inflate(parent.getContext(),R.layout.detail_about,null);
                return new DetailAboutHolder(context,view);
            }
            case TYPE_INSIGHTS:{
                view = View.inflate(parent.getContext(),R.layout.detail_insights,null);
                return new DetailInsightsHolder(context,view);
            }
            case TYPE_NEWS:{
//                System.out.println("news_cnt = "+ news_cnt);
                view = View.inflate(parent.getContext(),R.layout.detail_news_1,null);
//                news_cnt++;
                return new DetailNewsHolder(context,view);
            }
            case TYPE_FIRST_NEWS:{
//                System.out.println("news_cnt = "+ news_cnt);
                view = View.inflate(parent.getContext(),R.layout.detail_news_0,null);
                return new DetailNewsHolder(context,view);

            }
            case TYPE_NEWSTITLE:{
                view = View.inflate(parent.getContext(),R.layout.detail_newstitle,null);
                return new DetailNewstitleHolder(view);
            }
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        detail_stock_item item = items.get(position);
        if(item.type == TYPE_HEADLINE){
//            System.out.println("headline: " + position+ " " + item.getClass());
            DetailHeadlineHolder detailHeadlineHolder = (DetailHeadlineHolder) holder;
            detailHeadlineHolder.BindData(item);
        }else if(item.type == TYPE_CHARTONE){
//            System.out.println("chartone: " + position+ " " +item.getClass());
            DetailChartoneHolder detailChartoneHolder = (DetailChartoneHolder) holder;
            detailChartoneHolder.BindData(item);
        }else if(item.type == TYPE_PORTFOLIO){
//            System.out.println("portfolio: " + position+ " " +item.getClass());
            DetailPortfolioHolder detailPortfolioHolder = (DetailPortfolioHolder) holder;
            detailPortfolioHolder.BindData(item);
        }else if(item.type == TYPE_STATS){
//            System.out.println("stats: " + position+ " " +item.getClass());
            DetailStatsHolder detailStatsHolder = (DetailStatsHolder) holder;
            detailStatsHolder.BindData(item);
        }else if(item.type == TYPE_ABOUT){
//            System.out.println("about: " + position+ " " +item.getClass());
            DetailAboutHolder detailAboutsHolder = (DetailAboutHolder) holder;
            detailAboutsHolder.BindData(item);
        }else if(item.type == TYPE_INSIGHTS){
//            System.out.println("insights: " + position+ " " +item.getClass());
            DetailInsightsHolder detailInsightsHolder = (DetailInsightsHolder) holder;
            detailInsightsHolder.BindData(item);
        }else if(item.type  == TYPE_NEWS){
//            System.out.println("news: " + position+ " " +item.getClass());
            DetailNewsHolder detailNewsHolder = (DetailNewsHolder) holder;
            detailNewsHolder.BindData(item);
        }else if(item.type  == TYPE_FIRST_NEWS){
//            System.out.println("news: " + position+ " " +item.getClass());
            DetailNewsHolder detailNewsHolder = (DetailNewsHolder) holder;
            detailNewsHolder.BindData(item);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

class DetailHeadlineHolder extends RecyclerView.ViewHolder{
    final private Context context;
    final private TextView detail_item_ticker;
    final private TextView detail_item_closed_price;
    final private ImageView detail_item_trend;
    final private TextView detail_item_change;
    final private TextView item_shares_or_name;
    final public ImageView detail_item_logo;

    public DetailHeadlineHolder(Context context1, @NonNull View itemView) {
        super(itemView);
        this.detail_item_ticker = itemView.findViewById(R.id.detail_item_ticker);
        this.detail_item_closed_price = itemView.findViewById(R.id.detail_item_closed_price);
        this.detail_item_trend = itemView.findViewById(R.id.detail_item_trend);
        this.detail_item_change = itemView.findViewById(R.id.detail_item_change);
        this.item_shares_or_name = itemView.findViewById(R.id.item_shares_or_name);
        this.detail_item_logo = itemView.findViewById(R.id.detail_item_logo);
        this.context = context1;
    }

    public void BindData(detail_stock_item item){

        detail_headline item1 = (detail_headline)item;
        this.detail_item_ticker.setText(item1.ticker);
        this.detail_item_closed_price.setText("$" + df.format(item1.closed_price));
        this.item_shares_or_name.setText(item1.name);

        this.detail_item_change.setText(df.format(item1.change)+'('+df.format(item1.change_rate)+"%)");

        if(item1.change<0){
            this.detail_item_trend.setImageResource(R.drawable.trending_down);
            this.detail_item_change.setTextColor(context.getColor(R.color.red));
        }else if(item1.change == 0){
            this.detail_item_trend.setImageResource(android.R.color.transparent);
            this.detail_item_change.setTextColor(context.getColor(R.color.not_change_grey));
        }else{
            this.detail_item_trend.setImageResource(R.drawable.trending_up);
            this.detail_item_change.setTextColor(context.getColor(R.color.green));
        }


        if(Math.abs(item1.change - 0)<1E-4)item1.change =0.00;
        if(Math.abs(item1.change_rate - 0)<1E-4)item1.change_rate =0.00;
        this.detail_item_change.setText("$" + df.format(item1.change)+'('+df.format(item1.change_rate)+"%)");
        System.out.println("tem.change: " + item1.change);
        if(Math.abs(item1.change - 0)<1E-4){
            this.detail_item_trend.setImageResource(android.R.color.transparent);
            this.detail_item_change.setTextColor(context.getColor(R.color.not_change_grey));
        }else if(item1.change<0){
            this.detail_item_trend.setImageResource(R.drawable.trending_down);
            this.detail_item_change.setTextColor(context.getColor(R.color.red));
        }else{
            this.detail_item_trend.setImageResource(R.drawable.trending_up);
            this.detail_item_change.setTextColor(context.getColor(R.color.green));
        }


//        Picasso.get().load(@dra).into(detail_item_logo);
//        try{
//            URL url = new URL(item1.logo_url);
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            InputStream inputStream = urlConnection.getInputStream();
//            SVG svg = SVGParser. getSVGFromInputStream(inputStream);
//        }catch (Exception e){
//            System.out.println(e);
//        }


        RequestBuilder<PictureDrawable> requestBuilder;
        requestBuilder =
                GlideApp.with(context)
                        .as(PictureDrawable.class)
                        .transition(withCrossFade())
                        .listener(new SvgSoftwareLayerSetter());

        Uri uri = Uri.parse(item1.logo_url);
        requestBuilder.load(uri)

                .into(detail_item_logo);
//        try{
//            GlideApp.with(context)
//                    .load(item1.logo_url)
//                    .into(detail_item_logo);
//        }catch (Exception e){
//            System.out.println("error "+e);
//        }

        System.out.println("load avatar");
        System.out.println("logo : " + item1.logo_url);
    }
}
class DetailChartoneHolder extends RecyclerView.ViewHolder{

    final private Context context;
//    final private WebView vbp_chart;

    final private ViewPager viewPager;
    final private TabLayout tabLayout;
    public DetailChartoneHolder(Context context1, @NonNull View itemView) {
        super(itemView);
        this.context = context1;
//        this.vbp_chart = itemView.findViewById(R.id.vbp_chart);
        viewPager = itemView.findViewById(R.id.chartone_viewpager);
        tabLayout = itemView.findViewById(R.id.chartone_tablayout);
    }

    public void BindData(detail_stock_item item){
        detail_chartone item1 = (detail_chartone) item;
        tabLayout.setupWithViewPager(viewPager);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(((AppCompatActivity) context).getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPageAdapter.addFragment(new RealTimeStock(context,item1.ticker),"");
        viewPageAdapter.addFragment(new VbpStock(context,item1.ticker),"");
        viewPager.setAdapter(viewPageAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.chart_line);
        tabLayout.getTabAt(1).setIcon(R.drawable.clock_time_three);
    }
}
class DetailPortfolioHolder extends RecyclerView.ViewHolder implements TradeDialog.TradeDialogListener {
    final private Context context;
    final private TextView detail_item_sharesowned;
    final private TextView detail_item_avgcost;
    final private TextView detail_item_totalcost;
    final private TextView detail_item_change_port;
    final private TextView detail_item_marketvalue;
    final private Button detail_button_trade;
    private Double closed_price;
    private Double money;
    private String ticker;
    private String ticker_name;


    public DetailPortfolioHolder(Context context1, @NonNull View itemView) {
        super(itemView);
        context = context1;
        this.detail_item_sharesowned = itemView.findViewById(R.id.detail_item_sharesowned);
        this.detail_item_avgcost = itemView.findViewById(R.id.detail_item_avgcost);
        this.detail_item_totalcost = itemView.findViewById(R.id.detail_item_totalcost);
        this.detail_item_change_port = itemView.findViewById(R.id.detail_item_change_port);
        this.detail_item_marketvalue = itemView.findViewById(R.id.detail_item_marketvalue);
        this.detail_button_trade = itemView.findViewById(R.id.detail_button_trade);
        closed_price = 0.0;
        money = 0.0;
    }
    public void BindData(detail_stock_item item){
        detail_portfolio item1 = (detail_portfolio) item;
        System.out.println("shared_owned"+item1.share_owned);
        this.detail_item_sharesowned.setText(item1.share_owned.toString());
        this.detail_item_avgcost.setText("$" + df.format(item1.avg_cost));
        this.detail_item_totalcost.setText("$" + df.format(item1.total_cost));
        if(item1.share_owned == 0){
            this.detail_item_change_port.setText("$0.00");
            this.detail_item_marketvalue.setText("$" + df.format(item1.market_value));
        }
        else if(item1.share_owned != 0){
            double change = (item1.closed_price - item1.avg_cost)*item1.share_owned;
            this.detail_item_change_port.setText("$" + df.format((item1.closed_price - item1.avg_cost)*item1.share_owned));
            this.detail_item_marketvalue.setText("$" + df.format(item1.market_value));
            if(change > 0){
                this.detail_item_change_port.setTextColor(context.getResources().getColor(R.color.green));
                this.detail_item_marketvalue.setTextColor(context.getResources().getColor(R.color.green));
            }else if(change < 0){
                this.detail_item_change_port.setTextColor(context.getResources().getColor(R.color.red));
                this.detail_item_marketvalue.setTextColor(context.getResources().getColor(R.color.red));
            }
        }
        closed_price = item1.closed_price;
        money = item1.money;
        ticker = item1.ticker;
        ticker_name = item1.ticker_name;

        //set trade function
        this.detail_button_trade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("trade button clicked!");
                openTradeDialog(detail_item_sharesowned,
                                detail_item_avgcost,
                                detail_item_totalcost,
                                detail_item_change_port,
                                detail_item_marketvalue,
                                item1);
            }
        });
    }

    public void openTradeDialog(TextView detail_item_sharesowned,
                                TextView detail_item_avgcost,
                                TextView detail_item_totalcost,
                                TextView detail_item_change_port,
                                TextView detail_item_marketvalue,
                                detail_portfolio item1){
//        TradeDialog tradeDialog = new TradeDialog(context);
//        tradeDialog.show(((AppCompatActivity) this.context).getSupportFragmentManager(),"example dialog");

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.customdialog_1);
        dialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.custom_dialog_1_bac));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        Button buy = dialog.findViewById(R.id.dialog_trade_buy);
        Button sell = dialog.findViewById(R.id.dialog_trade_sell);
        TextView dialog_trade_totalformula = dialog.findViewById(R.id.dialog_trade_totalformula);
        TextView dialog_trade_moneytobuy = dialog.findViewById(R.id.dialog_trade_moneytobuy);
        TextView dialog_trade_title = dialog.findViewById(R.id.dialog_trade_title);
        double cur_money = sharedPreferences.getFloat("money",0);
        //设置标题
        dialog_trade_title.setText("Trade " + ticker_name + " shares");

        //限制只能输入数字
        EditText dialog_trade_number = dialog.findViewById(R.id.dialog_trade_number);
        dialog_trade_number.setInputType(InputType.TYPE_CLASS_NUMBER);
        //设置默认值
        dialog_trade_number.setHint("0");
        dialog_trade_moneytobuy.setText("$" + df.format(cur_money) + " to buy " + ticker);
        dialog_trade_totalformula.setText("0*$" + df.format(closed_price)+ "/share = 0.00");
        //监听内容变化
        dialog_trade_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                double cur_shares = 0;
                if(dialog_trade_number.getText().toString().equals(""))
                    cur_shares = 0;
                else
                    cur_shares = Double.parseDouble(dialog_trade_number.getText().toString());
                dialog_trade_totalformula.setText(cur_shares + "*$" + df.format(closed_price)+ "/share = " + df.format(cur_shares*closed_price));
                System.out.println("ticker: "+ticker);
                System.out.println("total price: "+ df.format(cur_shares*closed_price));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        //设定宽高
        int width = (int)(context.getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(context.getResources().getDisplayMetrics().heightPixels*0.42);
        dialog.getWindow().setLayout(width, height);


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double cur_shares;
                if(dialog_trade_number.length() == 0){

                    System.out.println("share is empty");
                    Toast.makeText(context, "Please enter a valid amount",
                            Toast.LENGTH_LONG).show();
                    cur_shares = 0;
                    return;
                }
                else cur_shares = Double.parseDouble(dialog_trade_number.getText().toString());

                double money = (double)sharedPreferences.getFloat("money",0);
                int shares = sharedPreferences.getInt("p_"+ticker+"_s",0);
                double avg_cost = sharedPreferences.getFloat("p_"+ticker+"_a",0);
                double total_cost = sharedPreferences.getFloat("p_"+ticker+"_t",0);

                //买入！
                //钱不够
                if(money < cur_shares * closed_price){
                    Toast.makeText(context, "Not enough money to buy",
                            Toast.LENGTH_LONG).show();
                    System.out.println("钱不够！！");
                    return;
                }
                //share为负数
                if(0 >= cur_shares * closed_price){
                    Toast.makeText(context, "Cannot buy non-positive shares",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                //share 不合法 浮点数
                int tem = (int)cur_shares;
                if((double)tem != cur_shares){
                    Toast.makeText(context, "Please enter a valid amount",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                //合法
                //更新钱包数据
                if(shares == 0){
                    String ports[] = String2StringArray(sharedPreferences.getString("p_ticker_names",""));
                    ArrayList<String> temp = new ArrayList<>(Arrays.asList(ports));
                    temp.add(ticker);
                    ports = new String[temp.size()];
                    for(int i=0;i<temp.size();i++)
                        ports[i] = temp.get(i);
                    editor.putString("p_ticker_names",StringArray2String(ports));
                }
                shares += (int)cur_shares;
                total_cost += cur_shares*closed_price;
                avg_cost = total_cost/shares;
                money -= cur_shares * closed_price;
                editor.putInt("p_"+ticker+"_s",shares);
                editor.putFloat("p_"+ticker+"_a",(float)avg_cost);
                editor.putFloat("p_"+ticker+"_t",(float)total_cost);
                editor.putFloat("money",(float)money);
                editor.apply();

                //更新portfolio视图
                detail_item_sharesowned.setText("" + shares);
                detail_item_avgcost.setText("$" + df.format(avg_cost));
                detail_item_totalcost.setText("$" + df.format(total_cost));
                detail_item_marketvalue.setText("$" + df.format((double)shares * item1.closed_price));


                System.out.println("dialog getContext:");
                System.out.println(dialog.getContext());
                dialog.dismiss();
                openCongDialog("You have successfully bought "+ (int)cur_shares + " shares of " + ticker);


            }
        });
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double cur_shares;
                if(dialog_trade_number.length() == 0){
                    System.out.println("share is empty");
                    Toast.makeText(context, "Please enter a valid amount",
                            Toast.LENGTH_LONG).show();
                    cur_shares = 0;
                    return;
                }
                else cur_shares = Double.parseDouble(dialog_trade_number.getText().toString());
                double money = (double)sharedPreferences.getFloat("money",0);
                int shares = sharedPreferences.getInt("p_"+ticker+"_s",0);
                double avg_cost = sharedPreferences.getFloat("p_"+ticker+"_a",0);
                double total_cost = sharedPreferences.getFloat("p_"+ticker+"_t",0);

                //share不够
                if(cur_shares > shares ){
                    Toast.makeText(context, "Not enough shares to sell",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                //share为负数
                if(0 >= cur_shares * closed_price){
                    Toast.makeText(context, "Cannot sell non-positive shares",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                //share 不合法 浮点数
                int tem = (int)cur_shares;
                if((double)tem != cur_shares){
                    Toast.makeText(context, "Please enter a valid amount",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                //合法
                //更新钱包数据
                shares -= (int)cur_shares;
                if(shares == 0){
                    String[] ports = String2StringArray(sharedPreferences.getString("p_ticker_names",""));
                    ports = removeString(ports,ticker);
                    editor.putString("p_ticker_names",StringArray2String(ports));
                    editor.apply();
                    avg_cost = 0;
                    detail_item_change_port.setTextColor(context.getColor(R.color.black));
                    detail_item_change_port.setText("$0");
                    detail_item_marketvalue.setTextColor(context.getColor(R.color.black));
                }
                total_cost = shares * avg_cost;
                money += cur_shares * closed_price;
                editor.putInt("p_"+ticker+"_s",shares);
                editor.putFloat("p_"+ticker+"_a",(float)avg_cost);
                editor.putFloat("p_"+ticker+"_t",(float)total_cost);
                editor.putFloat("money",(float)money);
                editor.apply();


                //更新portfolio视图
                detail_item_sharesowned.setText("" + shares);
                detail_item_avgcost.setText("$" + df.format(avg_cost));
                detail_item_totalcost.setText("$" + df.format(total_cost));
                detail_item_marketvalue.setText("$" + df.format((double)shares * item1.closed_price));

                dialog.dismiss();
                openCongDialog("You have successfully sell "+ (int)cur_shares + " shares of " + ticker);
            }
        });
        dialog.show();
    }

    public void openCongDialog(String success_info){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.customdialog_2);
        dialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.custom_dialog_2_bac));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        //设定宽高
        int width = (int)(context.getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(context.getResources().getDisplayMetrics().heightPixels*0.34);
        dialog.getWindow().setLayout(width, height);

        //更新成功信息
        TextView dialog_cong_successinfo = dialog.findViewById(R.id.dialog_cong_successinfo);
        dialog_cong_successinfo.setText(success_info);

        //Done按钮
        Button Done = dialog.findViewById(R.id.dialog_cong_done);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    public void applyTexts(String dialog_trade_number) {

    }
}
class DetailStatsHolder extends RecyclerView.ViewHolder{
   final private Context context;
   final private TextView detail_stats_openprice;
   final private TextView detail_stats_highprice;
   final private TextView detail_stats_lowprice;
   final private TextView detail_stats_prevclose;
    public DetailStatsHolder(Context context1, @NonNull View itemView) {
        super(itemView);
        this.context = context1;
        this.detail_stats_openprice = itemView.findViewById(R.id.detail_stats_openprice);
        this.detail_stats_highprice = itemView.findViewById(R.id.detail_stats_highprice);
        this.detail_stats_lowprice = itemView.findViewById(R.id.detail_stats_lowprice);
        this.detail_stats_prevclose = itemView.findViewById(R.id.detail_stats_prevclose);
    }

    public void BindData(detail_stock_item item){
        detail_stats item1 = (detail_stats) item;
        this.detail_stats_openprice.setText("$" + df.format(item1.open_price));
        this.detail_stats_highprice.setText("$" + df.format(item1.high_price));
        this.detail_stats_lowprice.setText("$" + df.format(item1.low_price));
        this.detail_stats_prevclose.setText("$" + df.format(item1.prev_close));
    }
}
class DetailAboutHolder extends RecyclerView.ViewHolder{
    final private Context context;
    final private TextView detail_about_ipo;
    final private TextView detail_about_industry;
    final private TextView detail_about_webpage;
    final private LinearLayout detail_about_peers;
    public DetailAboutHolder(Context context1, @NonNull View itemView) {
        super(itemView);
        context = context1;
        this.detail_about_ipo = itemView.findViewById(R.id.detail_about_ipo);
        this.detail_about_industry = itemView.findViewById(R.id.detail_about_industry);
        this.detail_about_webpage = itemView.findViewById(R.id.detail_about_webpage);
        this.detail_about_peers = itemView.findViewById(R.id.detail_about_peers_list);
    }
    public void BindData(detail_stock_item item){
        detail_about item1 = (detail_about) item;
        String[] tem = item1.ipo_start_date.split("-");
        String ipo_string = tem[1] + "-" + tem[2] + "-" + tem[0];
        this.detail_about_ipo.setText(ipo_string);
        this.detail_about_industry.setText(item1.industry);
        this.detail_about_webpage.setText(Html.fromHtml("<a href=\'" + item1.webpage + "\'>" + item1.webpage + "</a>"));
        this.detail_about_webpage.setMovementMethod(LinkMovementMethod.getInstance());
        this.detail_about_webpage.setLinkTextColor(context.getColor(R.color.tablayout_blue));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(20, 10, 20, 10);

        for (int i = 0; i < item1.company_peers.size(); i++)
        {
            String ticker = item1.company_peers.get(i);
            TextView peerView = new TextView(context);
            peerView.setClickable(true);
            peerView.setPaintFlags(peerView.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
            peerView.setText(item1.company_peers.get(i)+",");
            peerView.setTextColor(Color.BLUE);
            peerView.setLayoutParams(layoutParams);
            peerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("click on peer!" + ticker);
                    Intent detail = new Intent(context, DetailStockActivity.class);
                    detail.putExtra(SEARCHED_TICKER, ticker);
                    context.startActivity(detail);
                }
            });
            detail_about_peers.addView(peerView);
            detail_about_peers.invalidate();
        }

//        this.detail_about_peers.setClickable(true);
//        this.detail_about_peers.setText(item1.company_peers.get(0));
//        this.detail_about_peers.setPaintFlags(detail_about_peers.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
//        this.detail_about_peers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("click on peer!");
//                Intent detail = new Intent(context, DetailStockActivity.class);
//                detail.putExtra(SEARCHED_TICKER, "BIJIZE");
//                context.startActivity(detail);
//            }
//        });

    }
}
class DetailInsightsHolder extends RecyclerView.ViewHolder{
    final private Context context;
    final private TextView detail_insights_table_name;
    final private TextView total_reddit;
    final private TextView total_twitter;
    final private TextView positive_reddit;
    final private TextView positive_twitter;
    final private TextView negative_reddit;
    final private TextView negative_twitter;
    final private WebView webview_recommend;
    final private WebView webview_EPS;

    public DetailInsightsHolder(Context context,@NonNull View itemView ) {
        super(itemView);
        this.context = context;
        this.detail_insights_table_name = itemView.findViewById(R.id.detail_insights_table_name);
        this.total_reddit = itemView.findViewById(R.id.total_reddit);
        this.total_twitter = itemView.findViewById(R.id.total_twitter);
        this.positive_reddit = itemView.findViewById(R.id.positive_reddit);
        this.positive_twitter = itemView.findViewById(R.id.positive_twitter);
        this.negative_reddit = itemView.findViewById(R.id.negative_reddit);
        this.negative_twitter = itemView.findViewById(R.id.negative_twitter);
        this.webview_recommend = itemView.findViewById(R.id.webview_recommend);
        this.webview_EPS = itemView.findViewById(R.id.webview_EPS);
    }

    public void BindData(detail_stock_item item){
        detail_insights item1 = (detail_insights) item;
        this.detail_insights_table_name.setText(item1.fullname);
        this.total_reddit.setText(item1.total_reddit.toString());
        this.total_twitter.setText(item1.total_twitter.toString());
        this.positive_reddit.setText(item1.positive_reddit.toString());
        this.positive_twitter.setText(item1.positive_twitter.toString());
        this.negative_reddit.setText(item1.negative_reddit.toString());
        this.negative_twitter.setText(item1.negative_twitter.toString());

        //加载recommandation和eps两个图表
        WebSettings webSettings0 = webview_recommend.getSettings();
        webSettings0.setJavaScriptEnabled(true);
        VBPWebAppInterface vbpWebAppInterface0 = new VBPWebAppInterface(context,item1.ticker);
        webview_recommend.addJavascriptInterface(vbpWebAppInterface0,"Android");
        webview_recommend.loadUrl("file:///android_asset/chart_recommend.html");
        System.out.println("chart_recommend! load!");

        editor.putBoolean("detail_page_over",true);
        editor.putBoolean("chart_3",true);
        editor.apply();

        WebSettings webSettings1 = webview_EPS.getSettings();
        webSettings1.setJavaScriptEnabled(true);
        VBPWebAppInterface vbpWebAppInterface1 = new VBPWebAppInterface(context,item1.ticker);
        webview_EPS.addJavascriptInterface(vbpWebAppInterface1,"Android");
        webview_EPS.loadUrl("file:///android_asset/chart_eps.html");
        System.out.println("chart_eps! load!");
        editor.putBoolean("detail_page_over",true);
        editor.putBoolean("chart_4",true);
        editor.apply();
    }
}
class DetailNewstitleHolder extends RecyclerView.ViewHolder{
    public DetailNewstitleHolder(@NonNull View itemView) {
        super(itemView);
    }
    public void BindData(){};
}
class DetailNewsHolder extends RecyclerView.ViewHolder{
    final private Context context;
    final private TextView news_render;
    final private TextView news_time;
    final private TextView news_headline;
    final private ImageView news_image;

    public DetailNewsHolder(Context context, @NonNull View itemView) {
        super(itemView);
        this.context = context;
        this.news_render = itemView.findViewById(R.id.news_render);
        this.news_time = itemView.findViewById(R.id.news_time);
        this.news_headline = itemView.findViewById(R.id.news_headline);
        this.news_image = itemView.findViewById(R.id.news_image);
    }
    public void BindData(detail_stock_item item){
        detail_news item1 = (detail_news) item;
        this.news_render.setText(item1.render);

        long nowTime = getNowUnix();
        String title_time =(String) DateUtils.getRelativeTimeSpanString(item1.time*1000L,nowTime*1000L,3600*1000);
        this.news_time.setText(title_time);

        this.news_headline.setText(item1.headline);
//        Picasso.get().load(item1.image_url).into(this.news_image);
        Glide.with(context).load(item1.image_url).into(this.news_image);
        System.out.println("image_url:"+item1.image_url);
//        Glide.with(context).load("https://static2.finnhub.io/file/publicdatany/finnhubimage/stock_logo/AAPL.svg").into(this.news_image);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("news clicked!");
                openNewsDialog(item1);
            }
        });
    }
    public void openNewsDialog(detail_news item){
        Dialog dialog = new Dialog(context); 
        dialog.setContentView(R.layout.customdialog_3);


        String []month_list = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        Date date = new Date(item.time * 1000L);
        String dialog_date = month_list[date.getMonth()] + " " + date.getDate() + ", " + (date.getYear()+1900);

        ((TextView)(dialog.findViewById(R.id.news_render))).setText(item.render);
        ((TextView)(dialog.findViewById(R.id.news_dialogtime))).setText(dialog_date);
        ((TextView)(dialog.findViewById(R.id.news_headline))).setText(item.headline);
        ((TextView)(dialog.findViewById(R.id.news_content))).setText(item.description);

        dialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.custom_dialog_3_bac));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        //设定宽高
        int width = (int)(context.getResources().getDisplayMetrics().widthPixels*0.93);
        int height = (int)(context.getResources().getDisplayMetrics().heightPixels*0.60);
        dialog.getWindow().setLayout(width, height);
//        dialog.getWindow()

        dialog.findViewById(R.id.news_twitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("twitter clicked!");
                String url = "https://twitter.com/intent/tweet?text=" + "Check out this Link:" + item.Url + "%0a";
                System.out.println("url:"+url);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(browserIntent);
            }
        });
        dialog.findViewById(R.id.news_facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("facebook clicked!");

                //encodeURIComponent(str)
                String url = "https://www.facebook.com/sharer/sharer.php?u=" +item.Url +"&amp;src=sdkpreparse";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(browserIntent);
            }
        });

        dialog.findViewById(R.id.news_chrome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("chrome clicked!");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.Url));
                context.startActivity(browserIntent);
            }

        });
        dialog.show();

    }

}

