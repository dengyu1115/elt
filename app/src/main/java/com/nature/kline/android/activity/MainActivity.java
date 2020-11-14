package com.nature.kline.android.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.nature.kline.R;
import com.nature.kline.android.service.TaskService;
import com.nature.kline.android.util.ClickUtil;
import com.nature.kline.android.util.NotifyUtil;
import com.nature.kline.android.util.PopUtil;
import com.nature.kline.android.util.ViewUtil;
import com.nature.kline.common.ioc.starter.ComponentStarter;
import com.nature.kline.common.manager.*;
import com.nature.kline.common.util.InstanceHolder;

/**
 * 主入口activity
 * @author nature
 * @version 1.0.0
 * @since 2019/12/16 22:51
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String PERMISSION_RES = "android.permission.READ_EXTERNAL_STORAGE";
    private static final String PERMISSION_WES = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final String[] PERMISSIONS_STORAGE = {PERMISSION_RES, PERMISSION_WES};
    private final WorkDayManager workDayManager = InstanceHolder.get(WorkDayManager.class);
    private final ItemManager itemManager = InstanceHolder.get(ItemManager.class);
    private final KlineManager klineManager = InstanceHolder.get(KlineManager.class);
    private final NetManager netManager = InstanceHolder.get(NetManager.class);
    private final PriceNetManager priceNetManager = InstanceHolder.get(PriceNetManager.class);
    private final QuotaManager quotaManager = InstanceHolder.get(QuotaManager.class);
    private final ScaleManager scaleManager = InstanceHolder.get(ScaleManager.class);

    private Context context;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewUtil.initActivity(this);
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.verifyStoragePermissions(this);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        } else {    // 首次启动进行组件注入
            ComponentStarter.getInstance().start();
            NotifyUtil.context = MainActivity.this;
        }
        this.context = MainActivity.this;
    }

    public void initWorkdays(View view) {
        ClickUtil.asyncClick(view, () -> "工作日数据初始化完成:" + workDayManager.initWorkdays());
    }

    public void testNotification(View view) {
        ClickUtil.asyncClick(view, () -> {
            NotifyUtil.doNotify(context, 0, "test title", "test content");
            return null;
        });
    }

    public void startTaskService(View view) {
        ClickUtil.doClick(view, () -> this.startService(new Intent(context, TaskService.class)));
    }

    public void stopTaskService(View view) {
        ClickUtil.doClick(view, () -> this.stopService(new Intent(context, TaskService.class)));
    }

    public void toTaskList(View view) {
        ClickUtil.doClick(view, () -> startActivity(new Intent(context, TaskActivity.class)));
    }

    public void toLineView(View view) {
        ClickUtil.doClick(view, () -> startActivity(new Intent(context, LineViewActivity.class)));
    }

    public void toEditGroup(View view) {
        ClickUtil.doClick(view, () -> startActivity(new Intent(context, GroupEditActivity.class)));
    }

    public void toEditItemGroup(View view) {
        ClickUtil.doClick(view, () -> startActivity(new Intent(context, ItemGroupEditActivity.class)));
    }

    public void toTradeEdit(View view) {
        ClickUtil.doClick(view, () -> startActivity(new Intent(context, MarkEditActivity.class)));
    }

    public void toPriceNetList(View view) {
        ClickUtil.doClick(view, () -> startActivity(new Intent(context, PriceNetSearchActivity.class)));
    }

    public void toKlineList(View view) {
        ClickUtil.doClick(view, () -> startActivity(new Intent(context, KlineListActivity.class)));
    }

    public void toBsList(View view) {
        ClickUtil.doClick(view, () -> startActivity(new Intent(context, BsListActivity.class)));
    }

    public void toTradeList(View view) {
        ClickUtil.doClick(view, () -> startActivity(new Intent(context, MarkListActivity.class)));
    }

    public void toTargetList(View view) {
        ClickUtil.doClick(view, () -> startActivity(new Intent(context, TargetListActivity.class)));
    }

    public void toFundRate(View view) {
        ClickUtil.doClick(view, () -> startActivity(new Intent(context, FundRateActivity.class)));
    }

    public void toItemQuota(View view) {
        ClickUtil.doClick(view, () -> startActivity(new Intent(context, ItemQuotaActivity.class)));
    }

    public void toQuotaView(View view) {
        ClickUtil.doClick(view, () -> {
            Intent intent = new Intent(context, QuotaActivity.class);
            intent.putExtra("code", "000300");
            startActivity(intent);
        });
    }

    public void toDefineFundList(View view) {
        ClickUtil.doClick(view, () -> startActivity(new Intent(context, FundListDefActivity.class)));
    }

    public void loadLatestKline(View view) {
        ClickUtil.asyncClick(view, () -> String.format("K线最新加载完成,共%s条", klineManager.loadLatest()));
    }

    public void loadLatestNet(View view) {
        ClickUtil.asyncClick(view, () -> String.format("净值最新加载完成,共%s条", netManager.loadLatest()));
    }

    public void loadLatestQuota(View view) {
        ClickUtil.asyncClick(view, () -> String.format("指数指标最新加载完成,共%s条", quotaManager.loadLatest()));
    }

    public void reloadItem(View view) {
        PopUtil.confirm(context, "项目数据全部重载", "确定执行吗？", () -> {
            ClickUtil.asyncClick(view, () -> String.format("项目数据加载完成,共%s条", itemManager.reloadAll()));
        });
    }

    public void reloadKline(View view) {
        PopUtil.confirm(context, "K线数据全部重载", "确定执行吗？", () -> {
            ClickUtil.asyncClick(view, () -> String.format("K线历史加载完成,共%s条", klineManager.reloadAll()));
        });
    }

    public void reloadNet(View view) {
        PopUtil.confirm(context, "净值数据全部重载", "确定执行吗？", () -> {
            ClickUtil.asyncClick(view, () -> String.format("净值历史加载完成,共%s条", netManager.reloadAll()));
        });
    }

    public void reloadQuota(View view) {
        PopUtil.confirm(context, "指数指标数据全部重载", "确定执行吗？", () -> {
            ClickUtil.asyncClick(view, () -> String.format("指数指标数据加载完成,共%s条", quotaManager.loadAll()));
        });
    }

    public void reloadScale(View view) {
        PopUtil.confirm(context, "规模数据全部重载", "确定执行吗？", () -> {
            ClickUtil.asyncClick(view, () -> String.format("规模数据加载完成,共%s条", scaleManager.reloadAll()));
        });
    }

    public void calculateKlineAverage(View view) {
        ClickUtil.asyncClick(view, () -> String.format("K线均值计算完成,共%s条", klineManager.averageAll()));
    }

    public void recalculatePriceNet(View view) {
        PopUtil.confirm(context, "价值净值数据全部重载", "确定执行吗？", () -> {
            ClickUtil.asyncClick(view, () -> String.format("价值净值历史计算完成,共%s条", priceNetManager.recalculate()));
        });
    }

    public void calculatePriceNet(View view) {
        ClickUtil.asyncClick(view, () -> String.format("价值净值历史计算完成,共%s条", priceNetManager.calculate()));
    }

    private int count;

    public void switchMain(View view) {
        String tag = (String) view.getTag();
        View viewFore = findViewById(R.id.fore);
        View viewBack = findViewById(R.id.back);
        if (count == 5 && "back".equals(tag)) {
            count = 0;
            viewFore.setVisibility(View.VISIBLE);
            viewBack.setVisibility(View.GONE);
        } else if (count == 5 && "fore".equals(tag)) {
            count = 0;
            viewBack.setVisibility(View.VISIBLE);
            viewFore.setVisibility(View.GONE);
        } else {
            count++;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void verifyStoragePermissions(Activity activity) {
        // 检测是否有写的权限
        int permission = ActivityCompat.checkSelfPermission(activity, PERMISSION_WES);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // 没有写的权限，去申请写的权限，会弹出对话框
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

}
