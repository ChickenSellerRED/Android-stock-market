package com.example.myfirstapp.beens;

public class detail_stats extends detail_stock_item{
    final public Double open_price;
    final public Double high_price;
    final public Double low_price;
    final public Double prev_close;

    public detail_stats( Double open_price, Double high_price, Double low_price, Double prev_close) {
        super(3);
        this.open_price = open_price;
        this.high_price = high_price;
        this.low_price = low_price;
        this.prev_close = prev_close;
    }
}
