package com.nature.kline.common.model;

/**
 * 交易
 * @author nature
 * @version 1.0.0
 * @since 2020/11/7 9:09
 */
public class Mark extends Item {

    /**
     * 日期
     */
    private String date;
    /**
     * 价格
     */
    private Double price;
    /**
     * 补仓跌幅
     */
    private Double rateBuy;
    /**
     * 止盈涨幅
     */
    private Double rateSell;
    /**
     * 目标价：买入
     */
    private Double priceBuy;
    /**
     * 目标价：卖出
     */
    private Double priceSell;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRateBuy() {
        return rateBuy;
    }

    public void setRateBuy(Double rateBuy) {
        this.rateBuy = rateBuy;
    }

    public Double getRateSell() {
        return rateSell;
    }

    public void setRateSell(Double rateSell) {
        this.rateSell = rateSell;
    }

    public Double getPriceBuy() {
        return priceBuy;
    }

    public void setPriceBuy(Double priceBuy) {
        this.priceBuy = priceBuy;
    }

    public Double getPriceSell() {
        return priceSell;
    }

    public void setPriceSell(Double priceSell) {
        this.priceSell = priceSell;
    }
}
