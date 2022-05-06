package com.example.myfirstapp.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.holders.AutoCompleteItem;

import java.util.List;

public class AutoCompleteAdapter extends RecyclerView.Adapter {
    final private List<String> tickers;
    final private List<String> names;
    public AutoCompleteAdapter(List<String> t, List<String> n) {
        this.tickers = t;
        this.names = n;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = View.inflate(parent.getContext(),R.layout.auto_complete_item,null);
        return new AutoCompleteItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String ticker_i = tickers.get(position);
        String name_i = names.get(position);
        final AutoCompleteItem autoCompleteItem = (AutoCompleteItem) holder;
        autoCompleteItem.auto_complete_item_ticker.setText(ticker_i);
        autoCompleteItem.auto_complete_item_name.setText(name_i);
    }

    @Override
    public int getItemCount() {
        return tickers.size();
    }
}
