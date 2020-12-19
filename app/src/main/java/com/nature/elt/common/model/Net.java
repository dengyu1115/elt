package com.nature.elt.common.model;

/**
 * 净值
 * @author nature
 * @version 1.0.0
 * @since 2020/4/4 18:26
 */
public class Net extends Item {

    /**
     * 日期
     */
    private String date;
    /**
     * 当前最新净值
     */
    private Double net;
    /**
     * 增长率
     */
    private Double rate;
    /**
     * 总净值
     */
    private Double netTotal;
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

    public Double getNet() {
        return net;
    }

    public void setNet(Double net) {
        this.net = net;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(Double netTotal) {
        this.netTotal = netTotal;
    }

    public Double getRateTotal() {
        return rateTotal;
    }

    public void setRateTotal(Double rateTotal) {
        this.rateTotal = rateTotal;
    }
}
