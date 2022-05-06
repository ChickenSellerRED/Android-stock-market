package com.example.myfirstapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myfirstapp.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TradeDialog extends AppCompatDialogFragment {
    private EditText dialog_trade_number;
    private TradeDialogListener listener;
    private Context context;

    public TradeDialog(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AlertDialogCustom);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_trade,null);

        TextView title = new TextView(context);
        title.setText("My Custom Title");
        title.setPadding(0, 25, 0, 0);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(18);
        title.setTextColor(Color.BLACK);


        builder.setView(view)
                .setCustomTitle(title)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String trade_number = dialog_trade_number.getText().toString();
                    }
                });
        final AlertDialog dialog = builder.create();
        dialog_trade_number = view.findViewById(R.id.dialog_trade_number);
        // 限制输入为数字
        dialog_trade_number.setInputType(InputType.TYPE_CLASS_NUMBER);
        //按钮居中
//        final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//        LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
//        positiveButtonLL.gravity = ViewGroup.LayoutParams.MATCH_PARENT;
//        positiveButton.setLayoutParams(positiveButtonLL);


        return dialog;
    }
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        try {
//            listener = (TradeDialogListener) context;
//        } catch (ClassCastException  e){
//            throw  new ClassCastException(context.toString() +
//                    "must implement");
//        }
//
//    }

    public interface TradeDialogListener{
        void applyTexts(String dialog_trade_number);
    }


}
