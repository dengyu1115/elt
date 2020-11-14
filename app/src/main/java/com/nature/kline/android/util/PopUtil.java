package com.nature.kline.android.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * 弹窗工具
 * @author nature
 * @version 1.0.0
 * @since 2020/6/6 11:00
 */
public class PopUtil {

    public static void alert(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 确认框
     * @param context  context
     * @param title    标题
     * @param message  提示消息
     * @param runnable 执行逻辑
     */
    public static void confirm(Context context, String title, String message, Runnable runnable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确定", (dialogInterface, i) -> {
            runnable.run();
        });
        builder.setNegativeButton("取消", (dialogInterface, i) -> {
        });
        builder.show();
    }

    /**
     * 确认框
     * @param context  context
     * @param title    标题
     * @param view     自定义的页面
     * @param runnable 执行逻辑
     */
    public static void confirm(Context context, String title, View view, Runnable runnable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setView(view);
        builder.setPositiveButton("确定", (dialogInterface, i) -> {
            runnable.run();
        });
        builder.setNegativeButton("取消", (dialogInterface, i) -> {
        });
        builder.show();
    }
}
