package com.nature.elt.common.model;

/**
 * 卖出
 * @author nature
 * @version 1.0.0
 * @since 2020/11/8 11:16
 */
public class Target extends Item {

    /**
     * 日期
     */
    private String date;
    /**
     * 价格
     */
    private Double price;
    /**
     * 涨幅
     */
    private Double rate;
    /**
     * 对应买入交易
     */
    private Mark mark;

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

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }
}
