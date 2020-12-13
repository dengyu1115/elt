package com.nature.kline.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 默认的大盘指标
 * @author nature
 * @version 1.0.0
 * @since 2020/11/28 14:29
 */
public enum QuotaField {

    JG("01", "价格"),
    SY("02", "市盈"),
    GS("03", "股数"),
    SZ("04", "市值"),
    SZ_LT("05", "流通市值"),
    GB("06", "股本"),
    GB_LT("07", "流通股本");

    private final String code;
    private final String name;

    QuotaField(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static String getName(String code) {
        QuotaField[] values = values();
        for (QuotaField group : values) {
            if (group.code.equals(code)) return group.name;
        }
        return null;
    }

    public static List<String> codes() {
        return Arrays.stream(values()).map(QuotaField::getCode).collect(Collectors.toList());
    }
}
