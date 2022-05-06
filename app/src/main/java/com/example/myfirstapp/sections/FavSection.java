package com.example.myfirstapp.sections;

import static com.example.myfirstapp.MainActivity.StringArray2String;
import static com.example.myfirstapp.MainActivity.editor;
import static com.example.myfirstapp.MainActivity.favList;
import static com.example.myfirstapp.MainActivity.removeString;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.MainActivity;
import com.example.myfirstapp.R;
import com.example.myfirstapp.adapters.StockListAdapter;
import com.example.myfirstapp.beens.stock_item;
import com.example.myfirstapp.holders.DateHeaderViewHolder;
import com.example.myfirstapp.holders.PortHHeaderViewHolder;
import com.example.myfirstapp.holders.StockItemHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class FavSection extends Section {

    private Context context;
    private stock_item title;
    public ArrayList<stock_item> list;

    RecyclerView recyclerView;
    public StockListAdapter stockListAdapter;

    /**
     * Create a Section object based on {@link SectionParameters}.
     *
     * @param sectionParameters section parameters
     */
    public FavSection( Context context_1, @NonNull SectionParameters sectionParameters, stock_item title_1, ArrayList<stock_item> list_1){
        super(SectionParameters.builder()
                .itemResourceId(R.layout.item_stock_list)
                .headerResourceId(R.layout.item_title)
                .build());
        this.list =  list_1;
        this.context = context_1;
        this.title = title_1;
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

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();

            Collections.swap(list,from,to);

            recyclerView.getAdapter().notifyItemMoved(from,to);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            System.out.println("on swipe");
            int position = viewHolder.getAbsoluteAdapterPosition();
            switch(direction){
                case ItemTouchHelper.LEFT:
                    String removeTicker1 = list.get(position).ticker;
                    favList = removeString(favList,removeTicker1);
                    editor.putString("f_ticker_names",StringArray2String(favList));
                    editor.apply();
                    list.remove(position);
                    stockListAdapter.notifyItemRemoved(position);
                    break;
                case ItemTouchHelper.RIGHT:
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(context, R.color.delete_red))
                    .addActionIcon(R.drawable.delete)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };




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
