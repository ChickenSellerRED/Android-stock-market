package com.example.myfirstapp.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.beens.stock_item;

public class DateHeaderViewHolder extends RecyclerView.ViewHolder {
    final public TextView item;
    public DateHeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        item = itemView.findViewById(R.id.stock_date);
    }
}
