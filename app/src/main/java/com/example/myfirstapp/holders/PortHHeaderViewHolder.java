package com.example.myfirstapp.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.beens.stock_item;

public class PortHHeaderViewHolder extends RecyclerView.ViewHolder {
    final public TextView item;
    public PortHHeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        item = itemView.findViewById(R.id.title);
    }
}
