package com.nature.kline.android.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import androidx.appcompat.app.AppCompatActivity;
import com.nature.kline.android.util.ViewTemplate;
import com.nature.kline.android.util.ViewUtil;
import com.nature.kline.android.view.QuotaView;
import com.nature.kline.common.manager.QuotaManager;
import com.nature.kline.common.model.Quota;
import com.nature.kline.common.util.InstanceHolder;

import java.util.List;


/**
 * 债券线图
 * @author nature
 * @version 1.0.0
 * @since 2020/4/5 15:46
 */
public class QuotaActivity extends AppCompatActivity {

    private final QuotaManager quotaManager = InstanceHolder.get(QuotaManager.class);

    private Context context;
    private int width, height;
    private QuotaView view;
    private LinearLayout page;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = QuotaActivity.this;
        this.makeStructure();
        view.data(this.refreshData("000300"));
        this.setContentView(page);
        ViewUtil.initActivity(this);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }


    public void makeStructure() {
        ViewTemplate template = ViewTemplate.build(context);
        page = template.linearPage();
        page.setOrientation(LinearLayout.HORIZONTAL);
        page.setGravity(Gravity.CENTER);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        LinearLayout n1 = this.line(3);
        LinearLayout n2 = this.line(1);
        view = new QuotaView(context);
        n2.addView(view);
        page.addView(n2);
        page.addView(n1);
        n1.setOrientation(LinearLayout.VERTICAL);
        n1.addView(this.button("沪深300", "000300"));
        n1.addView(this.button("上证综指", "000001"));
        n1.addView(this.button("深证成指", "399001"));
        n1.addView(this.button("中小板指", "399005"));
        n1.addView(this.button("创业板指", "399006"));
    }

    private LinearLayout line(int weight) {
        LinearLayout line = new LinearLayout(context);
        line.setGravity(Gravity.CENTER);
        LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        param.weight = weight;
        line.setLayoutParams(param);
        return line;
    }

    private Button button(String name, String code) {
        Button button = new Button(context);
        button.setText(name);
        LayoutParams param = new LayoutParams((int) (width * 0.2d), (int) (height * 0.1d));
        button.setLayoutParams(param);
        button.setPadding(10, 10, 10, 10);
        button.setGravity(Gravity.CENTER);
        button.setOnClickListener(v -> view.data(this.refreshData(code)));
        return button;
    }

    private List<Quota> refreshData(String code) {
        return quotaManager.listByCode(code);
    }

}
