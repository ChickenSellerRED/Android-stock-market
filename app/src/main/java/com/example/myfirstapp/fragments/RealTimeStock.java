package com.example.myfirstapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.myfirstapp.webAppInterfaces.VBPWebAppInterface;


public class RealTimeStock extends Fragment {
    private final String ticker;
    Context context;

    public RealTimeStock(Context context1, String ticker1) {
        this.ticker = ticker1;
        context = context1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_real_time_stock, container, false);
        WebView webView = view.findViewById(R.id.chartone_realtime);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        VBPWebAppInterface vbpWebAppInterface = new VBPWebAppInterface(context,"TSLA");
        webView.addJavascriptInterface(vbpWebAppInterface,"Android");
        webView.loadUrl("file:///android_asset/sample.html");
        return view;
    }
}