package com.example.myfirstapp.beens;

public class detail_headline extends detail_stock_item{
    final public String logo_url;
    final public String ticker;
    final public String name;
    final public Double closed_price;
    public Double change;
    public Double change_rate;

    public detail_headline(String logo_url, String ticker, String name, Double closed_price, Double change, Double change_rate) {
        super(0);
        this.logo_url = logo_url;
        this.ticker = ticker;
        this.name = name;
        this.closed_price = closed_price;
        this.change = change;
        this.change_rate = change_rate;
    }
}
