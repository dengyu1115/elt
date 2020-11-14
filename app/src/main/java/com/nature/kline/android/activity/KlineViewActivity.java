package com.nature.kline.android.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.nature.kline.android.util.ViewUtil;
import com.nature.kline.android.view.KlineView;
import com.nature.kline.common.manager.KlineManager;
import com.nature.kline.common.model.Kline;
import com.nature.kline.common.util.InstanceHolder;

import java.util.List;

/**
 * 债券线图
 * @author nature
 * @version 1.0.0
 * @since 2020/4/5 15:46
 */
public class KlineViewActivity extends AppCompatActivity {

    private final KlineManager klineManager = InstanceHolder.get(KlineManager.class);

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KlineView klineView = new KlineView(this);
        klineView.data(this.data());
        this.setContentView(klineView);
        ViewUtil.initActivity(this);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private List<Kline> data() {
        String code = this.getIntent().getStringExtra("code");
        String market = this.getIntent().getStringExtra("market");
        return klineManager.list(code, market);
    }

}
