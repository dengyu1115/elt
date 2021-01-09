package com.nature.elt.common.activity;

import android.annotation.SuppressLint;
import android.widget.Button;
import com.nature.elt.common.enums.DateType;
import com.nature.elt.common.manager.WorkdayManager;
import com.nature.elt.common.model.Month;
import com.nature.elt.common.util.*;
import com.nature.elt.common.view.ExcelView;
import com.nature.elt.common.view.SearchBar;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务
 * @author nature
 * @version 1.0.0
 * @since 2020/3/7 17:32
 */
@SuppressLint("DefaultLocale")
public class WorkdayActivity extends BaseListActivity<Month> {

    private final WorkdayManager workDayManager = InstanceHolder.get(WorkdayManager.class);
    private Button year, reload, loadLatest;

    @Override
    protected List<ExcelView.D<Month>> define() {
        List<ExcelView.D<Month>> ds = new ArrayList<>();
        ds.add(new ExcelView.D<>("月份", i -> TextUtil.text(i.getMonth()), C, C, CommonUtil.nullsLast(Month::getMonth)));
        for (int i = 1; i < 32; i++) {
            String day = String.format("%02d", i);
            ds.add(new ExcelView.D<>(day, d -> TextUtil.text(this.getDateType(d, day)), C, C));
        }
        return ds;
    }

    private String getDateType(Month m, String day) {
        String date = String.format("%s-%s", m.getMonth(), day);
        String type = m.getDateType(date);
        return DateType.getDesc(type);
    }

    @Override
    protected List<Month> listData() {
        String date = this.year.getText().toString();
        if (StringUtils.isBlank(date)) return new ArrayList<>();
        return workDayManager.listYearMonths(date.substring(0, 4));
    }


    @Override
    protected void initHeaderViews(SearchBar searchBar) {
        searchBar.addConditionView(reload = template.button("重新加载", 100, 30));
        searchBar.addConditionView(loadLatest = template.button("加载最新", 100, 30));
        searchBar.addConditionView(year = template.button(100, 30));
    }

    @Override
    protected void initHeaderBehaviours() {
        year.setOnClickListener(v -> template.datePiker(year));
        reload.setOnClickListener(v ->
                PopUtil.confirm(context, "重新加载数据", "确定重新加载吗？",
                        () -> {
                            String date = this.year.getText().toString();
                            if (StringUtils.isBlank(date)) {
                                throw new RuntimeException("请选择年份");
                            }
                            ClickUtil.asyncClick(v, () -> String.format("加载完成,共%s条",
                                    workDayManager.reloadAll(date.substring(0, 4))));
                        }
                )
        );
        loadLatest.setOnClickListener(v ->
                ClickUtil.asyncClick(v, () -> {
                    String date = this.year.getText().toString();
                    if (StringUtils.isBlank(date)) {
                        throw new RuntimeException("请选择年份");
                    }
                    return String.format("加载完成,共%s条", workDayManager.loadLatest(date.substring(0, 4)));
                }));
    }

}
