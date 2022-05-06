package com.example.myfirstapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.beens.stock_item;
import com.example.myfirstapp.holders.PortHItemHolder;
import com.example.myfirstapp.holders.StockItemHolder;

import java.text.DecimalFormat;
import java.util.List;

public class PortHListAdapter extends RecyclerView.Adapter {
    private final List<stock_item> walletdata;
    Context context;

    public PortHListAdapter(List<stock_item> walletdata, Context context) {
        this.walletdata = walletdata;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = View.inflate(parent.getContext(), R.layout.item_portfolio, null);
        return new PortHItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        stock_item item = walletdata.get(position);
        PortHItemHolder portHItemHolder =(PortHItemHolder) holder;

        DecimalFormat df = new DecimalFormat("0.00");
        portHItemHolder.real_time_value.setText("$" + df.format(item.real_time_value));
        portHItemHolder.current_money.setText("$" + df.format(item.current_money));
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
