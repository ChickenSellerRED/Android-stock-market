package com.example.myfirstapp.sections;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.adapters.StockListAdapter;
import com.example.myfirstapp.beens.stock_item;
import com.example.myfirstapp.holders.DateHeaderViewHolder;
import com.example.myfirstapp.holders.PortHHeaderViewHolder;
import com.example.myfirstapp.holders.StockItemHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;


public class PortBSection extends Section {

    private Context context;
    public ArrayList<stock_item> list;

    public RecyclerView recyclerView;
    public StockListAdapter stockListAdapter;

    /**
     * Create a Section object based on {@link SectionParameters}.
     *
     * @param sectionParameters section parameters
     */
    public PortBSection(Context context_1, @NonNull SectionParameters sectionParameters, ArrayList<stock_item> list_1){
        super(SectionParameters.builder()
                .itemResourceId(R.layout.item_stock_list)
                .headerResourceId(R.layout.null_header)
                .build());
        this.list =  list_1;
        this.context = context_1;

    }


    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new StockItemHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        recyclerView = holder.itemView.findViewById(R.id.stock_recyclerview);
        //set manager
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //set adapter
        stockListAdapter = new StockListAdapter(list,context);
        recyclerView.setAdapter(stockListAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();

            Collections.swap(list, from, to);

            recyclerView.getAdapter().notifyItemMoved(from, to);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        }
    };

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
    public void updateData(){
        this.stockListAdapter.notifyDataSetChanged();
    }
}
