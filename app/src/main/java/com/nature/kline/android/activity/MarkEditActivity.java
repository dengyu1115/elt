package com.nature.kline.android.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.fastjson.JSON;
import com.nature.kline.android.util.PopUtil;
import com.nature.kline.android.util.ViewTemplate;
import com.nature.kline.android.util.ViewUtil;
import com.nature.kline.android.view.Selector;
import com.nature.kline.common.constant.DefaultGroup;
import com.nature.kline.common.manager.ItemGroupManager;
import com.nature.kline.common.manager.MarkManager;
import com.nature.kline.common.model.Item;
import com.nature.kline.common.model.Mark;
import com.nature.kline.common.util.InstanceHolder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 交易录入
 * @author nature
 * @version 1.0.0
 * @since 2020/11/7 10:01
 */
public class MarkEditActivity extends AppCompatActivity {

    private Context context;

    private LinearLayout page;
    private EditText keyword, price, rateBuy, rateSell;
    private Button date, save;
    private Selector<String> typeSelector;
    private Selector<Item> itemSelector;
    private ViewTemplate template;

    private final ItemGroupManager itemGroupManager = InstanceHolder.get(ItemGroupManager.class);
    private final MarkManager markManager = InstanceHolder.get(MarkManager.class);

    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = MarkEditActivity.this;
        this.makeStructure();
        this.initBehaviours();
        this.setContentView(page);
        ViewUtil.initActivity(MarkEditActivity.this);
    }

    private void initBehaviours() {
        itemSelector.mapper(Item::getName).onChangeRun(this.change()).init();
        Runnable run = () -> {
            List<Item> items = this.items = itemGroupManager.listItem(typeSelector.getValue());
            String keyword = this.keyword.getText().toString();
            List<Item> li = items.stream().filter(i -> i.getName().contains(keyword)).collect(Collectors.toList());
            itemSelector.refreshData(li);
        };
        typeSelector.mapper(DefaultGroup::getName).onChangeRun(run).init().refreshData(DefaultGroup.codes());
        this.initSelected();
        date.setOnClickListener(v -> template.datePiker(date));
        keyword.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                List<Item> li = items.stream().filter(i -> i.getName().contains(keyword)).collect(Collectors.toList());
                itemSelector.refreshData(li);
            }
        });
        save.setOnClickListener(v -> {
            Item item = this.itemSelector.getValue();
            if (item == null) {
                PopUtil.alert(context, "请选择项目");
                return;
            }
            String date = this.date.getText().toString();
            if (date.isEmpty()) {
                PopUtil.alert(context, "请选择日期");
                return;
            }
            String price = this.price.getText().toString();
            if (price.isEmpty()) {
                PopUtil.alert(context, "请填写价格");
                return;
            }
            String rateBuy = this.rateBuy.getText().toString();
            if (rateBuy.isEmpty()) {
                PopUtil.alert(context, "请填写补仓跌幅");
                return;
            }
            String rateSell = this.rateSell.getText().toString();
            if (rateSell.isEmpty()) {
                PopUtil.alert(context, "请填写补仓跌幅");
                return;
            }

            PopUtil.confirm(context, "操作确认", "确定保存吗？", () -> {
                Mark mark = new Mark();
                mark.setCode(item.getCode());
                mark.setMarket(item.getMarket());
                mark.setDate(date);
                mark.setPrice(Double.valueOf(price));
                mark.setRateBuy(Double.valueOf(rateBuy));
                mark.setRateSell(Double.valueOf(rateSell));
                markManager.merge(mark);
                PopUtil.alert(context, "保存成功");
            });
        });

    }

    private void initSelected() {
        Intent intent = this.getIntent();
        String type = intent.getStringExtra("type");
        Item item = JSON.parseObject(intent.getStringExtra("item"), Item.class);
        if (item == null) {
            return;
        }
        this.typeSelector.setValue(type);
        this.itemSelector.setValue(item);
        this.recommend();
    }


    private Runnable change() {
        return this::recommend;
    }

    @SuppressLint("DefaultLocale")
    private void recommend() {
        Item item = itemSelector.getValue();
        Mark mark = markManager.recommend(item);
        if (mark == null) {
            this.date.setText("");
            this.price.setText("");
            this.rateBuy.setText("");
            this.rateSell.setText("");
        } else {
            this.date.setText(mark.getDate());
            this.price.setText(String.valueOf(mark.getPrice()));
            this.rateBuy.setText(String.format("%.4f", mark.getRateBuy()));
            this.rateSell.setText(String.format("%.4f", mark.getRateSell()));
        }
    }

    public void makeStructure() {
        template = ViewTemplate.build(context);
        page = template.linearPage();
        page.setGravity(Gravity.CENTER);
        LinearLayout l1 = template.line(300, 30);
        LinearLayout l2 = template.line(300, 30);
        LinearLayout l3 = template.line(300, 30);
        LinearLayout l4 = template.line(300, 30);
        LinearLayout l5 = template.line(300, 30);
        LinearLayout l6 = template.line(300, 30);
        LinearLayout l7 = template.line(300, 60);
        LinearLayout l8 = template.line(300, 30);
        l1.addView(template.textView("类型：", 80, 30));
        l1.addView(typeSelector = template.selector(100, 30));
        l1.addView(keyword = template.editText(100, 30));
        l2.addView(template.textView("项目：", 80, 30));
        l2.addView(itemSelector = template.selector(200, 30));
        l3.addView(template.textView("日期：", 80, 30));
        l3.addView(date = template.button(200, 30));
        l4.addView(template.textView("价格：", 80, 30));
        l4.addView(price = template.numeric(200, 30));
        l5.addView(template.textView("补仓跌幅：", 80, 30));
        l5.addView(rateBuy = template.numeric(200, 30));
        l6.addView(template.textView("止盈涨幅：", 80, 30));
        l6.addView(rateSell = template.numeric(200, 30));
        l8.addView(save = template.button("保存", 60, 30));
        page.addView(l1);
        page.addView(l2);
        page.addView(l3);
        page.addView(l4);
        page.addView(l5);
        page.addView(l6);
        page.addView(l7);
        page.addView(l8);
    }

}
