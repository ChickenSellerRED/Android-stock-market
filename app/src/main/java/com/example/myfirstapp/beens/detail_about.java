package com.example.myfirstapp.beens;

import java.util.List;

public class detail_about extends detail_stock_item{

    final public String ipo_start_date;
    final public String industry;
    final public String webpage;
    final public List<String> company_peers;

    public detail_about(String ipo_start_date, String industry, String webpage, List<String> company_peers) {
        super(4);
        this.ipo_start_date = ipo_start_date;
        this.industry = industry;
        this.webpage = webpage;
        this.company_peers = company_peers;
    }

}
