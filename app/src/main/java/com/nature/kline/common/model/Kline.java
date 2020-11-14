package com.nature.kline.common.model;

/**
 * kline
 * @author nature
 * @version 1.0.0
 * @since 2020/4/4 18:26
 */
public class Kline extends Item {
    /**
     * 日期
     */
    private String date;
    /**
     * 开盘价
     */
    private Double open;
    /**
     * 最新价
     */
    private Double latest;
    /**
     * 最高价
     */
    private Double high;
    /**
     * 最低价
     */
    private Double low;
    /**
     * 交易份额数
     */
    private Double share;
    /**
     * 交易金额数
     */
    private Double amount;
    /**
     * 规模
     */
    private Double scale;
    /**
     * 周平均
     */
    private Double avgWeek;
    /**
     * 月平均
     */
    private Double avgMonth;
    /**
     * 季平均
     */
    private Double avgSeason;
    /**
     * 年平均
     */
    private Double avgYear;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getLatest() {
        return latest;
    }

    public void setLatest(Double latest) {
        this.latest = latest;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getShare() {
        return share;
    }

    public void setShare(Double share) {
        this.share = share;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }

    public Double getAvgWeek() {
        return avgWeek;
    }

    public void setAvgWeek(Double avgWeek) {
        this.avgWeek = avgWeek;
    }

    public Double getAvgMonth() {
        return avgMonth;
    }

    public void setAvgMonth(Double avgMonth) {
        this.avgMonth = avgMonth;
    }

    public Double getAvgSeason() {
        return avgSeason;
    }

    public void setAvgSeason(Double avgSeason) {
        this.avgSeason = avgSeason;
    }

    public Double getAvgYear() {
        return avgYear;
    }

    public void setAvgYear(Double avgYear) {
        this.avgYear = avgYear;
    }
}
