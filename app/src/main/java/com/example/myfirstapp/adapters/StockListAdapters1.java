package com.example.myfirstapp.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.beens.stock_item;

import java.util.List;

public class StockListAdapters1 extends RecyclerView.Adapter {

    private final List<stock_item> stockdata;
    public static final int TYPE_DATE = 0;
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_ITEM = 2;
    public static final int TYPE_PORTFOLIO = 3;

    //set data
    public StockListAdapters1(List<stock_item> data) {
        this.stockdata = data;
    }


    // create View of items
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //transfer the ui
        //1.get view
        //2.create InnerHolder
        View view;
        if (viewType == TYPE_DATE) {
            view = View.inflate(parent.getContext(), R.layout.item_date, null);
            return new DateHolder(view);
        } else if (viewType == TYPE_TITLE) {
            view = View.inflate(parent.getContext(), R.layout.item_title, null);
            return new TitleHolder(view);
        } else if (viewType == TYPE_ITEM) {
            view = View.inflate(parent.getContext(), R.layout.item_stock, null);
            return new StockHolder(view);
        } else {
            view = View.inflate(parent.getContext(), R.layout.item_portfolio, null);
            return new PortfolioHolder(view);
        }
    }

    //bind InnerHolder, to set data
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //设置数据
    }

    @Override
    public int getItemViewType(int position) {
        stock_item tem = stockdata.get(position);
        if (tem.type == 0)
            return TYPE_DATE;
        else if (tem.type == 1)
            return TYPE_TITLE;
        else if(tem.type ==2)
            return TYPE_ITEM;
        else return TYPE_PORTFOLIO;
    }

    //return item counts
    @Override
    public int getItemCount() {
        if (stockdata != null)
            return stockdata.size();
        return 0;
    }

    public class DateHolder extends RecyclerView.ViewHolder {

        public DateHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class TitleHolder extends RecyclerView.ViewHolder {

        public TitleHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class StockHolder extends RecyclerView.ViewHolder {

        public StockHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class PortfolioHolder extends RecyclerView.ViewHolder {

        public PortfolioHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
