package com.nature.kline.common.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum DefaultGroup {

    INDEX("INDEX", "指数"),
    FUND("FUND", "基金");

    private String code;

    private String name;

    private DefaultGroup(String code, String name) {
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
        DefaultGroup[] values = values();
        for (DefaultGroup group : values) {
            if (group.code.equals(code)) return group.name;
        }
        return null;
    }

    public static List<String> codes() {
        return Arrays.stream(values()).map(DefaultGroup::getCode).collect(Collectors.toList());
    }
}
