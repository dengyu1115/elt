package com.nature.kline.common.model;

/**
 * kline
 * @author nature
 * @version 1.0.0
 * @since 2020/4/4 18:26
 */
public class PriceNet extends BaseModel {
    /**
     * 编号
     */
    private String code;
    /**
     * 日期
     */
    private String date;
    /**
     * 名称
     */
    private String name;
    /**
     * 上次收盘价
     */
    private Double priceLast;
    /**
     * 当前最新价
     */
    private Double priceLatest;
    /**
     * 最高价
     */
    private Double priceHigh;
    /**
     * 最低价
     */
    private Double priceLow;
    /**
     * 上次净值
     */
    private Double netLast;
    /**
     * 当前最新净值
     */
    private Double netLatest;
    /**
     * 价格涨幅 (priceLatest-priceLast)/priceLast
     */
    private Double ratePrice;
    /**
     * 净值涨幅 (netLatest-netLast)/netLast
     */
    private Double rateNet;
    /**
     * 折价率 (netLatest-priceLatest)/priceLatest
     */
    private Double rateDiff;
    /**
     * 市值
     */
    private Double scale;
    /**
     * 交易金额
     */
    private Double amount;
    /**
     * 交易金额占比 (amount/scale)
     */
    private Double rateAmount;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPriceLast() {
        return priceLast;
    }

    public void setPriceLast(Double priceLast) {
        this.priceLast = priceLast;
    }

    public Double getPriceLatest() {
        return priceLatest;
    }

    public void setPriceLatest(Double priceLatest) {
        this.priceLatest = priceLatest;
    }

    public Double getPriceHigh() {
        return priceHigh;
    }

    public void setPriceHigh(Double priceHigh) {
        this.priceHigh = priceHigh;
    }

    public Double getPriceLow() {
        return priceLow;
    }

    public void setPriceLow(Double priceLow) {
        this.priceLow = priceLow;
    }

    public Double getNetLast() {
        return netLast;
    }

    public void setNetLast(Double netLast) {
        this.netLast = netLast;
    }

    public Double getNetLatest() {
        return netLatest;
    }

    public void setNetLatest(Double netLatest) {
        this.netLatest = netLatest;
    }

    public Double getRatePrice() {
        return ratePrice;
    }

    public void setRatePrice(Double ratePrice) {
        this.ratePrice = ratePrice;
    }

    public Double getRateNet() {
        return rateNet;
    }

    public void setRateNet(Double rateNet) {
        this.rateNet = rateNet;
    }

    public Double getRateDiff() {
        return rateDiff;
    }

    public void setRateDiff(Double rateDiff) {
        this.rateDiff = rateDiff;
    }

    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getRateAmount() {
        return rateAmount;
    }

    public void setRateAmount(Double rateAmount) {
        this.rateAmount = rateAmount;
    }
}
