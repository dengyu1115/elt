package com.nature.elt.item.model;

import com.nature.elt.common.model.BaseModel;

/**
 * 项目
 * @author nature
 * @version 1.0.0
 * @since 2020/4/4 18:22
 */
public class Item extends BaseModel {
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 市场
     */
    private String market;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }
}
