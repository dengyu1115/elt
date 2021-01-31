package com.nature.elt.item.model;

import com.nature.elt.common.model.BaseModel;

/**
 * 净值
 * @author nature
 * @version 1.0.0
 * @since 2020/4/4 18:26
 */
public class Scale extends BaseModel {
    /**
     * 编号
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 日期
     */
    private String date;
    /**
     * 规模金额
     */
    private Double amount;
    /**
     * 变动
     */
    private Double change;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }
}
