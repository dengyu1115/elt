package com.nature.elt.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 任务状态
 * @author nature
 * @version 1.0.0
 * @since 2020/12/6 13:46
 */
public enum TaskStatus {

    VALID("1", "有效"),
    INVALID("0", "无效");


    private String code, name;

    TaskStatus(String code, String name) {
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
        return Arrays.stream(values()).map(TaskStatus::getCode).collect(Collectors.toList());
    }

    public static String getName(String code) {
        TaskStatus[] values = values();
        for (TaskStatus v : values) {
            if (v.getCode().equals(code)) {
                return v.getName();
            }
        }
        return null;
    }
}
