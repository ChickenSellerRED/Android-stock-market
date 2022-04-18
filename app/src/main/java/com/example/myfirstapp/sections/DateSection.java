package com.example.myfirstapp.sections;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.beens.stock_item;
import com.example.myfirstapp.holders.DateHeaderViewHolder;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;


public class DateSection extends Section {

    private stock_item item;

    /**
     * Create a Section object based on {@link SectionParameters}.
     *
     * @param sectionParameters section parameters
     */
    public DateSection(@NonNull SectionParameters sectionParameters, stock_item item_1){
        super(SectionParameters.builder()
                .itemResourceId(R.layout.null_body)
                .headerResourceId(R.layout.item_date)
                .build());
        this.item = item_1;
    }


    @Override
    public int getContentItemsTotal() {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return null;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(final View view) {
        return new DateHeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(final RecyclerView.ViewHolder holder) {
        final DateHeaderViewHolder headerHolder = (DateHeaderViewHolder) holder;
        if(item.type == 0)
            headerHolder.item.setText(item.date);
    }
}
