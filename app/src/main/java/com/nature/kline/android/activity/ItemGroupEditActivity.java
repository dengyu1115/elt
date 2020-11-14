package com.nature.kline.android.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import androidx.appcompat.app.AppCompatActivity;
import com.nature.kline.android.util.TextUtil;
import com.nature.kline.android.util.ViewTemplate;
import com.nature.kline.android.util.ViewUtil;
import com.nature.kline.android.view.ExcelView;
import com.nature.kline.android.view.Selector;
import com.nature.kline.common.manager.GroupManager;
import com.nature.kline.common.manager.ItemGroupManager;
import com.nature.kline.common.manager.ItemManager;
import com.nature.kline.common.model.Group;
import com.nature.kline.common.model.Item;
import com.nature.kline.common.model.ItemGroup;
import com.nature.kline.common.util.CommonUtil;
import com.nature.kline.common.util.InstanceHolder;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static android.widget.LinearLayout.LayoutParams.MATCH_PARENT;

/**
 * 项目分组编辑
 * @author nature
 * @version 1.0.0
 * @since 2020/6/13 17:28
 */
public class ItemGroupEditActivity extends AppCompatActivity {

    private Context context;

    private LinearLayout page;

    private ViewTemplate template;

    private final ItemManager itemManager = InstanceHolder.get(ItemManager.class);
    private final GroupManager groupManager = InstanceHolder.get(GroupManager.class);
    private final ItemGroupManager itemGroupManager = InstanceHolder.get(ItemGroupManager.class);

    private String lkw, rkw, group;

    private static final int CENTER = 0, START = 1;

    private final List<ExcelView.D<Item>> lds = Arrays.asList(
            new ExcelView.D<>("名称", d -> TextUtil.text(d.getName()), CENTER, START, CommonUtil.nullsLast(Item::getName), toKlineView()),
            new ExcelView.D<>("CODE", d -> TextUtil.text(d.getCode()), CENTER, START, CommonUtil.nullsLast(Item::getCode), toKlineView()),
            new ExcelView.D<>("操作", d -> "+", CENTER, CENTER, this.leftClick())
    );

    private final List<ExcelView.D<Item>> rds = Arrays.asList(
            new ExcelView.D<>("名称", d -> TextUtil.text(d.getName()), CENTER, START, CommonUtil.nullsLast(Item::getName), toKlineView()),
            new ExcelView.D<>("CODE", d -> TextUtil.text(d.getCode()), CENTER, START, CommonUtil.nullsLast(Item::getCode)),
            new ExcelView.D<>("操作", d -> "—", CENTER, CENTER, this.rightClick())
    );

    private Consumer<Item> leftClick() {
        return i -> {
            ItemGroup ig = new ItemGroup();
            ig.setGroup(this.group);
            ig.setCode(i.getCode());
            ig.setMarket(i.getMarket());
            ig.setName(i.getName());
            ig.setType(i.getType());
            itemGroupManager.merge(ig);
            this.refreshRightExcel();
        };
    }

    private Consumer<Item> rightClick() {
        return i -> {
            itemGroupManager.delete(this.group, i.getCode(), i.getMarket());
            this.refreshRightExcel();
        };
    }

    private Consumer<Item> toKlineView() {
        return d -> {
            Intent intent = new Intent(getApplicationContext(), KlineViewActivity.class);
            intent.putExtra("code", d.getCode());
            intent.putExtra("market", d.getMarket());
            this.startActivity(intent);
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = ItemGroupEditActivity.this;
        this.makeStructure();
        this.initBehaviours();
        this.setContentView(page);
        ViewUtil.initActivity(ItemGroupEditActivity.this);
    }

    private void initBehaviours() {
        this.selector.mapper(Group::getName).init().refreshData(groupManager.list(null));
        group = this.selector.getValue().getCode();
        this.toAdd.define(lds);
        this.toAdd.data(itemManager.listByKeyWord(lkw));
        this.added.define(rds);
        this.added.data(itemGroupManager.listItem(group, rkw));
        this.change.setOnClickListener(v -> this.refreshRightExcel());
        this.tQuery.setOnClickListener(v -> {
            lkw = this.tText.getText().toString();
            this.toAdd.data(itemManager.listByKeyWord(lkw));
        });
        this.aQuery.setOnClickListener(v -> this.refreshRightExcel());
    }

    private void refreshRightExcel() {
        group = this.selector.getValue().getCode();
        rkw = this.aText.getText().toString();
        this.added.data(itemGroupManager.listItem(group, rkw));
    }

    private float uw, uh;
    public EditText tText, aText;
    public Button change, tQuery, aQuery;
    public ExcelView<Item> toAdd, added;
    public Selector<Group> selector;

    private void makeStructure() {
        template = ViewTemplate.build(context);
        page = new LinearLayout(context);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setGravity(Gravity.CENTER);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        this.uw = metrics.widthPixels / 100f;
        this.uh = metrics.heightPixels / 100f;
        this.header();
        this.body();
        this.footer();
    }

    private void header() {
        LinearLayout line = this.block(100, 5);
        page.addView(line);
        line.addView(selector = new Selector<>(context));
        line.addView(change = template.button("切换分组", 60, 30));
        LinearLayout layout = this.block(100, 5);
        page.addView(layout);
        LinearLayout left = this.block(50, 5);
        left.addView(tText = template.editText(120, 30));
        left.addView(tQuery = template.button("查询", 60, 30));
        LinearLayout right = this.block(50, 5);
        right.addView(aText = template.editText(120, 30));
        right.addView(aQuery = template.button("查询", 60, 30));
        layout.addView(left);
        layout.addView(right);
    }

    private void body() {
        LinearLayout layout = this.block(100, 90);
        LayoutParams param = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        layout.setLayoutParams(param);
        page.addView(layout);
        LayoutParams p = new LayoutParams(width(50), MATCH_PARENT);
        LinearLayout left = this.block(50, 90);
        left.setLayoutParams(p);
        LinearLayout right = this.block(50, 90);
        right.setLayoutParams(p);
        left.addView(toAdd = new ExcelView<>(context, 3, 0.49f));
        right.addView(added = new ExcelView<>(context, 3, 0.49f));
        layout.addView(left);
        layout.addView(divider());
        layout.addView(right);
    }

    private void footer() {

    }

    private View divider() {
        View view = new View(context);
        LayoutParams param = new LayoutParams(1, -1);
        view.setLayoutParams(param);
        view.setBackgroundColor(Color.LTGRAY);
        return view;
    }

    private LinearLayout block(int width, int height) {
        LinearLayout layout = new LinearLayout(context);
        LayoutParams param = new LayoutParams(width(width), height(height));
        layout.setLayoutParams(param);
        layout.setGravity(Gravity.CENTER);
        return layout;
    }

    private int width(float percent) {
        return (int) (uw * percent + 0.5f);
    }

    private int height(float percent) {
        return (int) (uh * percent + 0.5f);
    }
}
