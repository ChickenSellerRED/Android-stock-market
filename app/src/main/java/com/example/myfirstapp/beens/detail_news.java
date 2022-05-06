package com.example.myfirstapp.beens;

public class detail_news extends detail_stock_item{
    //type = 6
    //一条新闻的数据
    final public String render;
    final public long time;
    final public String headline;
    final public String image_url;
    final public String description;
    final public String Url;

    public detail_news(String render, long time, String headline, String image_url, String description, String Url,int index) {
        super(index);
        this.render = render;
        this.time = time;
        this.headline = headline;
        this.image_url = image_url;
        this.description = description;
        this.Url = Url;

    }
}
