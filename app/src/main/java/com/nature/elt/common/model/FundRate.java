package com.nature.elt.common.model;

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
     * 涨幅map
     */
    private final Map<String, Double> rates = new HashMap<>();
    /**
     * 净资产规模
     */
    private Double scale;

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
