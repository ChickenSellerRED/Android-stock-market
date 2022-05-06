package com.example.myfirstapp.holders;


import com.example.myfirstapp.R;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AutoCompleteItem extends RecyclerView.ViewHolder {
    final public TextView auto_complete_item_ticker;
    final public TextView auto_complete_item_name;

    public AutoCompleteItem(@NonNull View itemView) {
        //这里view是一个layout的View
        super(itemView);
        this.auto_complete_item_ticker = itemView.findViewById(R.id.auto_complete_item_ticker);
        this.auto_complete_item_name = itemView.findViewById(R.id.auto_complete_item_name);
    }
}
