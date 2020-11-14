package com.nature.kline.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 任务持有
 * @author nature
 * @version 1.0.0
 * @since 2020/2/27 16:21
 */
public class TaskHolder {

    /**
     * 方法实例的map
     */
    private static final Map<String, Method> methods = new HashMap<>();
    private static final Map<String, Object> instances = new HashMap<>();

    /**
     * 任务持有
     * @param code code
     */
    public static void invoke(String code) {
        try {
            methods.get(code).invoke(instances.get(code));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static void put(String code, Method m, Object o) {
        methods.put(code, m);
        instances.put(code, o);
    }
}
