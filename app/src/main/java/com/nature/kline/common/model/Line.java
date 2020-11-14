package com.nature.kline.common.model;

/**
 * 折线
 * @author nature
 * @version 1.0.0
 * @since 2020/8/30 21:49
 */
public class Line extends BaseModel {
    /**
     * 日期
     */
    private String date;
    /**
     * 价格
     */
    private Double price;
    /**
     * 增长率
     */
    private Double rate;
    /**
     * 总增长率
     */
    private Double rateTotal;

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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getRateTotal() {
        return rateTotal;
    }

    public void setRateTotal(Double rateTotal) {
        this.rateTotal = rateTotal;
    }
}
