package com.example.myfirstapp.holders;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;

public class StockItemHolder extends RecyclerView.ViewHolder {
    final public TextView item_ticker;
    final public TextView item_closed_price;
    final public TextView item_change;
    final public TextView item_shares_or_name;
    final public ImageView item_trend;

    public StockItemHolder(@NonNull View itemView) {
        super(itemView);

        item_ticker = itemView.findViewById(R.id.item_ticker);
        item_closed_price = itemView.findViewById(R.id.item_closed_price);
        item_change = itemView.findViewById(R.id.item_change);
        item_shares_or_name = itemView.findViewById(R.id.item_shares_or_name);
        item_trend = itemView.findViewById(R.id.item_trend);
    }
}
