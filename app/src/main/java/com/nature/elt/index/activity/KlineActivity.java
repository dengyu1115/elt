package com.nature.elt.index.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.nature.elt.android.util.ViewUtil;
import com.nature.elt.android.view.KlineView;
import com.nature.elt.index.manager.KlineManager;
import com.nature.elt.index.model.Kline;
import com.nature.elt.common.util.InstanceHolder;

import java.util.List;

/**
 * K线图
 * @author nature
 * @version 1.0.0
 * @since 2020/11/24 19:11
 */
public class KlineActivity extends AppCompatActivity {

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
