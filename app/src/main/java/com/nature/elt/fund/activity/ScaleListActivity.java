package com.nature.elt.fund.activity;

import com.nature.elt.android.activity.BaseListActivity;
import com.nature.elt.android.util.TextUtil;
import com.nature.elt.android.view.ExcelView;
import com.nature.elt.android.view.SearchBar;
import com.nature.elt.fund.manager.ScaleManager;
import com.nature.elt.fund.model.Scale;
import com.nature.elt.common.util.CommonUtil;
import com.nature.elt.common.util.InstanceHolder;

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
    }

    @Override
    protected void initHeaderBehaviours() {
    }

}
