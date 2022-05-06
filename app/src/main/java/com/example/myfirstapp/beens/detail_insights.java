package com.example.myfirstapp.beens;

public class detail_insights extends detail_stock_item{
    final public String fullname;
    final public Integer total_reddit;
    final public Integer total_twitter;
    final public Integer positive_reddit;
    final public Integer positive_twitter;
    final public Integer negative_reddit;
    final public Integer negative_twitter;
    final public String ticker;

    //还有sentiment,eps两个表格的数据


    public detail_insights(String fullname, Integer total_reddit, Integer total_twitter, Integer positive_reddit, Integer positive_twitter, Integer negative_reddit, Integer negative_twitter, String ticker) {
        super(5);
        this.fullname = fullname;
        this.total_reddit = total_reddit;
        this.total_twitter = total_twitter;
        this.positive_reddit = positive_reddit;
        this.positive_twitter = positive_twitter;
        this.negative_reddit = negative_reddit;
        this.negative_twitter = negative_twitter;
        this.ticker = ticker;
    }

}
