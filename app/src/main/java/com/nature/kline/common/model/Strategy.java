package com.nature.kline.common.model;

import java.util.List;

/**
 * 策略
 * @author nature
 * @version 1.0.0
 * @since 2020/9/19 12:07
 */
public class Strategy extends BaseModel {
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
     * 折线定义集合
     */
    private List<LineDef> list;

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

    public List<LineDef> getList() {
        return list;
    }

    public void setList(List<LineDef> list) {
        this.list = list;
    }
}
