package com.nature.elt.item.activity;

import android.widget.Button;
import android.widget.EditText;
import com.nature.elt.android.activity.BaseListActivity;
import com.nature.elt.android.util.PopUtil;
import com.nature.elt.android.util.TextUtil;
import com.nature.elt.android.view.ExcelView;
import com.nature.elt.android.view.SearchBar;
import com.nature.elt.item.manager.ItemManager;
import com.nature.elt.item.model.Item;
import com.nature.elt.common.util.InstanceHolder;
import com.nature.elt.common.util.Sorter;

import java.util.Arrays;
import java.util.List;

/**
 * 项目指标
 * @author nature
 * @version 1.0.0
 * @since 2020/11/24 19:09
 */
public class ItemListActivity extends BaseListActivity<Item> {

    private final ItemManager itemManager = InstanceHolder.get(ItemManager.class);

    private EditText keyword;
    private Button loadLatest;

    private final List<ExcelView.D<Item>> ds = Arrays.asList(
            new ExcelView.D<>("名称", d -> TextUtil.text(d.getName()), C, S, Sorter.nullsLast(Item::getName)),
            new ExcelView.D<>("CODE", d -> TextUtil.text(d.getCode()), C, C, Sorter.nullsLast(Item::getCode)),
            new ExcelView.D<>("市场", d -> TextUtil.text(d.getMarket()), C, E, Sorter.nullsLast(Item::getMarket)),
            new ExcelView.D<>("类型", d -> TextUtil.text(d.getType()), C, E, Sorter.nullsLast(Item::getType))
    );

    protected List<Item> listData() {
        return itemManager.listByKeyWord(this.keyword.getText().toString());
    }

    @Override
    protected void initHeaderViews(SearchBar searchBar) {
        searchBar.addConditionView(loadLatest = template.button("加载最新", 80, 30));
        searchBar.addConditionView(keyword = template.editText(80, 30));
    }

    @Override
    protected void initHeaderBehaviours() {
        loadLatest.setOnClickListener(v ->
                PopUtil.confirm(context, "重新加载项目数据", "确定重新加载吗？", itemManager::reloadAll));
    }

    @Override
    protected List<ExcelView.D<Item>> define() {
        return ds;
    }

}
