package com.nature.kline.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 执行状态
 * @author nature
 * @version 1.0.0
 * @since 2020/12/6 13:46
 */
public enum ExeStatus {

    START("0", "执行中"),
    END("1", "执行结束"),
    EXCEPTION("2", "执行异常");


    private String code, name;

    ExeStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static List<String> codes() {
        return Arrays.stream(values()).map(ExeStatus::getCode).collect(Collectors.toList());
    }

    public static String getName(String code) {
        ExeStatus[] values = values();
        for (ExeStatus v : values) {
            if (v.getCode().equals(code)) {
                return v.getName();
            }
        }
        return null;
    }
}
