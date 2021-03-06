package com.nature.elt.common.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import java.util.function.Supplier;

/**
 * 点击工具类
 * @author nature
 * @version 1.0.0
 * @since 2019/10/24 22:05
 */
public class ClickUtil {

    private static long millis;

    /**
     * 点击（防止重复点击消耗资源）
     * @param view     view
     * @param runnable 点击后执行的逻辑
     */
    public static void doClick(View view, Runnable runnable) {
        view.setClickable(false);
        try {
            long currMillis = System.currentTimeMillis();
            if (currMillis - millis < 1000) {
                throw new RuntimeException("点击过于频繁");
            }
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
            PopUtil.alert(view.getContext(), e.getMessage());
        } finally {
            view.setClickable(true);
            millis = System.currentTimeMillis();
        }
    }

    /**
     * 点击（防止重复点击消耗资源）
     * @param view     view
     * @param supplier 点击后执行的逻辑
     */
    public static void asyncClick(View view, Supplier<String> supplier) {
        Handler handler = new Handler(msg -> {
            PopUtil.alert(view.getContext(), msg.getData().getString("data"));
            return false;
        });
        new Thread(() -> {
            view.setClickable(false);
            try {
                long currMillis = System.currentTimeMillis();
                if (currMillis - millis < 1000) {
                    throw new RuntimeException("点击过于频繁");
                }
                String s = supplier.get();
                if (s != null) {
                    handler.sendMessage(message(s));
                }
            } catch (Exception e) {
                e.printStackTrace();
                handler.sendMessage(message(e.getMessage()));
            } finally {
                view.setClickable(true);
                millis = System.currentTimeMillis();
            }
        }).start();
    }

    private static Message message(String content) {
        Message msg = new Message();
        Bundle data = new Bundle();
        data.putString("data", content);
        msg.setData(data);
        return msg;
    }
}
