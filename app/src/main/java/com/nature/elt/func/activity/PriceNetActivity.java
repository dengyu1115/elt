package com.nature.elt.func.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.nature.elt.func.manager.PriceNetManager;
import com.nature.elt.func.model.PriceNet;
import com.nature.elt.common.util.InstanceHolder;
import com.nature.elt.common.util.ViewUtil;
import com.nature.elt.common.view.PriceNetView;

import java.util.List;

/**
 * 债券线图
 * @author nature
 * @version 1.0.0
 * @since 2020/4/5 15:46
 */
public class PriceNetActivity extends AppCompatActivity {

    private final PriceNetManager priceNetManager = InstanceHolder.get(PriceNetManager.class);

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PriceNetView priceNetView = new PriceNetView(this);
        priceNetView.data(this.data());
        this.setContentView(priceNetView);
        ViewUtil.initActivity(this);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private List<PriceNet> data() {
        String code = this.getIntent().getStringExtra("code");
        return priceNetManager.listByCode(code);
    }

}
