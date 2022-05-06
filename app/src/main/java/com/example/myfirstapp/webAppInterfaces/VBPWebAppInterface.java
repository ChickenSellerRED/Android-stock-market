package com.example.myfirstapp.webAppInterfaces;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class VBPWebAppInterface {
    Context context;
    final private String ticker;

    public VBPWebAppInterface(Context context, String ticker) {
        this.context = context;
        this.ticker = ticker;
    }

    @JavascriptInterface
    public String getTicker(){
        return ticker;
    }
}
