package com.example.myfirstapp.sections;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.beens.stock_item;
import com.example.myfirstapp.holders.DateHeaderViewHolder;
import com.example.myfirstapp.holders.PortHHeaderViewHolder;
import com.example.myfirstapp.holders.PortHItemHolder;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;


public class PortHSection extends Section {

    private stock_item title;
    private List<stock_item> list;

    /**
     * Create a Section object based on {@link SectionParameters}.
     *
     * @param sectionParameters section parameters
     */
    public PortHSection(@NonNull SectionParameters sectionParameters, stock_item item_1, List<stock_item> list_1){
        super(SectionParameters.builder()
                .itemResourceId(R.layout.item_portfolio)
                .headerResourceId(R.layout.item_title)
                .build());
        this.title = item_1;
        this.list =  list_1;
    }


    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new PortHItemHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PortHItemHolder itemHolder = (PortHItemHolder) holder;
        final stock_item tem = list.get(position);

        itemHolder.current_money.setText(tem.current_money.toString());
        itemHolder.real_time_value.setText(tem.real_time_value.toString());
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(final View view) {
        return new PortHHeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(final RecyclerView.ViewHolder holder) {
        final PortHHeaderViewHolder headerHolder = (PortHHeaderViewHolder) holder;
        headerHolder.item.setText(title.title);
    }
}
