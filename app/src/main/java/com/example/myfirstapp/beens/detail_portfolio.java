package com.example.myfirstapp.beens;

public class detail_portfolio extends detail_stock_item{
    final public Integer share_owned;
    final public Double avg_cost;
    final public Double total_cost;
    final public Double change;
    final public Double market_value;
    final public Double closed_price;
    final public Double money;
    final public String ticker;
    final public String ticker_name;

    public detail_portfolio(Integer share_owned, Double avg_cost, Double total_cost, Double change, Double market_value,Double closed_value, Double money,String ticker,String ticker_name) {
        super(2);
        this.share_owned = share_owned;
        this.avg_cost = avg_cost;
        this.total_cost = total_cost;
        this.change = change;
        this.market_value = market_value;
        this.closed_price = closed_value;
        this.money = money;
        this.ticker = ticker;
        this.ticker_name = ticker_name;
    }
}
