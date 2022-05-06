package com.example.myfirstapp.adapters;

import static com.example.myfirstapp.MainActivity.SEARCHED_TICKER;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.DetailStockActivity;
import com.example.myfirstapp.MainActivity;
import com.example.myfirstapp.R;
import com.example.myfirstapp.beens.stock_item;
import com.example.myfirstapp.holders.StockItemHolder;

import java.text.DecimalFormat;
import java.util.List;

public class StockListAdapter extends RecyclerView.Adapter{

    private final List<stock_item> stockdata;
    Context context;

    public StockListAdapter(List<stock_item> stockdata, Context context) {
        this.stockdata = stockdata;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = View.inflate(parent.getContext(),R.layout.item_stock, null);
        return new StockItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final StockItemHolder itemHolder = (StockItemHolder) holder;
        final stock_item tem = stockdata.get(position);
        DecimalFormat df = new DecimalFormat("0.00");

        itemHolder.item_ticker.setText(tem.ticker);
        itemHolder.item_shares_or_name.setText(tem.shares_or_name);
        itemHolder.item_closed_price.setText(df.format(tem.closed_price));
        if(Math.abs(tem.change - 0)<1E-4)tem.change =0.00;
        if(Math.abs(tem.change_rate - 0)<1E-4)tem.change_rate =0.00;
        itemHolder.item_change.setText("$" + df.format(tem.change)+'('+df.format(tem.change_rate)+"%)");
        System.out.println("tem.change: " + tem.change);
        if(Math.abs(tem.change - 0)<1E-4){
            itemHolder.item_trend.setImageResource(android.R.color.transparent);
            itemHolder.item_change.setTextColor(context.getColor(R.color.not_change_grey));
        }else if(tem.change<0){
            itemHolder.item_trend.setImageResource(R.drawable.trending_down);
            itemHolder.item_change.setTextColor(context.getColor(R.color.red));
        }else{
            itemHolder.item_trend.setImageResource(R.drawable.trending_up);
            itemHolder.item_change.setTextColor(context.getColor(R.color.green));
        }
        itemHolder.to_detail_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(context, DetailStockActivity.class);
                detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                detail.putExtra(SEARCHED_TICKER, tem.ticker);
                context.startActivity(detail);
            }
        });

    }



    @Override
    public int getItemCount() {
        return stockdata.size();
    }
}
