package com.example.myfirstapp.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;

import org.w3c.dom.Text;

public class PortHItemHolder extends RecyclerView.ViewHolder {
    final public TextView net_worth;
    final public TextView cash_balance;
    final public TextView real_time_value;
    final public TextView current_money;
    public PortHItemHolder(@NonNull View itemView) {
        super(itemView);

        net_worth = itemView.findViewById(R.id.net_worth);
        cash_balance = itemView.findViewById(R.id.cash_balance);
        real_time_value = itemView.findViewById(R.id.real_time_value);
        current_money = itemView.findViewById(R.id.current_money);
    }
}
