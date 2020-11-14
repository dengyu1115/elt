package com.nature.kline.android.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.fastjson.JSON;
import com.nature.kline.android.util.TextUtil;
import com.nature.kline.android.util.ViewTemplate;
import com.nature.kline.android.util.ViewUtil;
import com.nature.kline.common.model.PriceNet;


public class PriceNetDetailActivity extends AppCompatActivity {

    private Context context;
    private PriceNet priceNet;
    private LinearLayout page;
    private ViewTemplate template;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewUtil.initActivity(this);
        super.onCreate(savedInstanceState);
        this.context = PriceNetDetailActivity.this;
        this.priceNet = JSON.parseObject(this.getIntent().getStringExtra("detail"), PriceNet.class);
        this.makeStructure();
        this.setContentView(page);

    }

    public void makeStructure() {
        template = ViewTemplate.build(context);
        page = template.linearPage();
        page.setOrientation(LinearLayout.VERTICAL);
        page.setGravity(Gravity.TOP | Gravity.CENTER);
        this.header();
        this.body();
        this.footer();
    }

    private void header() {

    }

    private void body() {
        LinearLayout l1 = template.line(800, 30);
        LinearLayout l2 = template.line(800, 30);
        LinearLayout l3 = template.line(800, 30);
        LinearLayout l4 = template.line(800, 30);
        LinearLayout l5 = template.line(800, 30);
        LinearLayout l6 = template.line(800, 30);
        l1.addView(template.textView("名称：", 80, 30));
        l1.addView(template.textView(TextUtil.text(priceNet.getName()), 100, 30));
        l1.addView(template.textView("编号：", 80, 30));
        l1.addView(template.textView(TextUtil.text(priceNet.getCode()), 100, 30));
        l2.addView(template.textView("净值昨收：", 80, 30));
        l2.addView(template.textView(TextUtil.net(priceNet.getNetLast()), 100, 30));
        l2.addView(template.textView("净值最新：", 80, 30));
        l2.addView(template.textView(TextUtil.net(priceNet.getNetLatest()), 100, 30));
        l3.addView(template.textView("价格昨收：", 80, 30));
        l3.addView(template.textView(TextUtil.net(priceNet.getPriceLast()), 100, 30));
        l3.addView(template.textView("价格最新：", 80, 30));
        l3.addView(template.textView(TextUtil.net(priceNet.getPriceLatest()), 100, 30));
        l4.addView(template.textView("规模：", 80, 30));
        l4.addView(template.textView(TextUtil.amount(priceNet.getScale()), 100, 30));
        l4.addView(template.textView("交易额：", 80, 30));
        l4.addView(template.textView(TextUtil.amount(priceNet.getAmount()), 100, 30));
        l5.addView(template.textView("净值增长率：", 80, 30));
        l5.addView(template.textView(TextUtil.hundred(priceNet.getRateNet()), 100, 30));
        l5.addView(template.textView("价格增长率：", 80, 30));
        l5.addView(template.textView(TextUtil.hundred(priceNet.getRatePrice()), 100, 30));
        l6.addView(template.textView("折价率：", 80, 30));
        l6.addView(template.textView(TextUtil.hundred(priceNet.getRateDiff()), 100, 30));
        l6.addView(template.textView("交易额占比：", 80, 30));
        l6.addView(template.textView(TextUtil.hundred(priceNet.getRateAmount()), 100, 30));
        page.addView(l1);
        page.addView(l2);
        page.addView(l3);
        page.addView(l4);
        page.addView(l5);
        page.addView(l6);
    }

    private void footer() {

    }

}
