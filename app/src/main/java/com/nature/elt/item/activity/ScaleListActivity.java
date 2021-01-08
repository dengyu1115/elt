package com.nature.elt.item.activity;

import android.widget.Button;
import com.nature.elt.common.activity.BaseListActivity;
import com.nature.elt.common.util.*;
import com.nature.elt.common.view.ExcelView;
import com.nature.elt.common.view.SearchBar;
import com.nature.elt.item.manager.ScaleManager;
import com.nature.elt.item.model.Scale;

import java.util.Arrays;
import java.util.List;

/**
 * 规模
 * @author nature
 * @version 1.0.0
 * @since 2020/11/21 12:13
 */
public class ScaleListActivity extends BaseListActivity<Scale> {

    private final ScaleManager scaleManager = InstanceHolder.get(ScaleManager.class);

    private String name;

    private final List<ExcelView.D<Scale>> ds = Arrays.asList(
            new ExcelView.D<>("name", d -> TextUtil.text(name), C, S),
            new ExcelView.D<>("code", d -> TextUtil.text(d.getCode()), C, S, CommonUtil.nullsLast(Scale::getCode)),
            new ExcelView.D<>("日期", d -> TextUtil.text(d.getDate()), C, C, CommonUtil.nullsLast(Scale::getDate)),
            new ExcelView.D<>("金额", d -> TextUtil.amount(d.getAmount()), C, C, CommonUtil.nullsLast(Scale::getAmount)),
            new ExcelView.D<>("变动", d -> TextUtil.percent(d.getChange()), C, C, CommonUtil.nullsLast(Scale::getChange))
    );

    private Button reload;

    @Override
    protected List<ExcelView.D<Scale>> define() {
        return ds;
    }

    @Override
    protected List<Scale> listData() {
        String code = this.getIntent().getStringExtra("code");
        name = this.getIntent().getStringExtra("name");
        return scaleManager.listByCode(code);
    }

    @Override
    protected void initHeaderViews(SearchBar searchBar) {
        searchBar.addConditionView(reload = template.button("重新加载", 80, 30));
    }

    @Override
    protected void initHeaderBehaviours() {
        reload.setOnClickListener(v ->
                PopUtil.confirm(context, "重新加载数据", "确定重新加载吗？",
                        () -> ClickUtil.asyncClick(v, () -> String.format("加载完成,共%s条", scaleManager.reloadAll()))
                )
        );

    }

}
