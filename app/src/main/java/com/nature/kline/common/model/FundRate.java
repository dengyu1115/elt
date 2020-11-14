package com.nature.kline.common.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 基金涨幅
 * @author nature
 * @version 1.0.0
 * @since 2020/9/16 22:29
 */
public class FundRate extends Net {

    /**
     * 名称
     */
    private String name;
    /**
     * 净资产规模
     */
    private Double scale;
    /**
     * 涨幅map
     */
    private final Map<String, Double> rates = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }

    public void putRate(String key, Double value) {
        rates.put(key, value);
    }

    public Double getRate(String key) {
        return rates.get(key);
    }
}
