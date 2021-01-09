package com.nature.elt.common.enums;

/**
 * 日期类型
 * @author nature
 * @version 1.0.0
 * @since 2021/1/9 14:09
 */
public enum DateType {

    WORKDAY("0", "工作日"), holiday("1", "节假日");

    DateType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDesc(String code) {
        DateType[] values = values();
        for (DateType v : values) {
            if (v.getCode().equals(code)) {
                return v.getDesc();
            }
        }
        return null;
    }
}
