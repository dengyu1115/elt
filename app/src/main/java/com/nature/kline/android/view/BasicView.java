package com.nature.kline.android.view;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

public class BasicView extends LinearLayout {

    protected Context context;
    protected static final int MATCH_PARENT = LayoutParams.MATCH_PARENT;
    protected static final int WRAP_CONTENT = LayoutParams.WRAP_CONTENT;
    protected final float DENSITY;

    public BasicView(Context context) {
        super(context);
        this.context = context;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.DENSITY = displayMetrics.density;
    }

    protected int dpToPx(float dp) {
        return (int) (dp * DENSITY + 0.5f);
    }

    protected float pxToDp(float px) {
        return px / DENSITY;
    }
}
