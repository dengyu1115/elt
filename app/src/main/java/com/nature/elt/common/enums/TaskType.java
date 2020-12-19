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
public enum TaskType {

    IN_WORKDAY("0", "工作日执行"),
    AFTER_WORKDAY("1", "工作日后执行");


    private String code, name;

    TaskType(String code, String name) {
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
        return Arrays.stream(values()).map(TaskType::getCode).collect(Collectors.toList());
    }

    public static String getName(String code) {
        TaskType[] values = values();
        for (TaskType v : values) {
            if (v.getCode().equals(code)) {
                return v.getName();
            }
        }
        return null;
    }
}
