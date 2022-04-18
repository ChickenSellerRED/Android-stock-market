package com.example.myfirstapp.sections;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.beens.stock_item;
import com.example.myfirstapp.holders.DateHeaderViewHolder;
import com.example.myfirstapp.holders.PortHHeaderViewHolder;
import com.example.myfirstapp.holders.StockItemHolder;

import java.util.Date;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;


public class PortBSection extends Section {

//    private Context context;
    private List<stock_item> list;

    /**
     * Create a Section object based on {@link SectionParameters}.
     *
     * @param sectionParameters section parameters
     */
    public PortBSection( @NonNull SectionParameters sectionParameters, List<stock_item> list_1){
        super(SectionParameters.builder()
                .itemResourceId(R.layout.item_stock)
                .headerResourceId(R.layout.null_header)
                .build());
        this.list =  list_1;

    }


    @Override
    public int getContentItemsTotal() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new StockItemHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final StockItemHolder itemHolder = (StockItemHolder) holder;
        final stock_item tem = list.get(position);

        itemHolder.item_ticker.setText(tem.ticker);
        itemHolder.item_shares_or_name.setText(tem.shares_or_name);
        itemHolder.item_closed_price.setText(tem.closed_price.toString());
        itemHolder.item_change.setText(tem.change.toString()+'('+tem.change_rate.toString()+"%)");
//        if(tem.change<0){
//            itemHolder.item_trend.setImageResource(R.drawable.trending_down);
//            itemHolder.item_change.setTextColor(context.getColor(R.color.red));
//        }else if(tem.change == 0){
//            itemHolder.item_trend.setImageResource(android.R.color.transparent);
//            itemHolder.item_change.setTextColor(context.getColor(R.color.black));
//        }else{
//            itemHolder.item_trend.setImageResource(R.drawable.trending_up);
//            itemHolder.item_change.setTextColor(context.getColor(R.color.green));
//        }
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(final View view) {
        return new PortHHeaderViewHolder(view);
//        return null;
    }

    @Override
    public void onBindHeaderViewHolder(final RecyclerView.ViewHolder holder) {
//        final PortHHeaderViewHolder headerHolder = (PortHHeaderViewHolder) holder;
//        headerHolder.item.setText("portfolio");
    }
}
