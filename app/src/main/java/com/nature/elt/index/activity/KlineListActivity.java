package com.nature.elt.index.activity;

import android.content.Intent;
import android.widget.EditText;
import com.nature.elt.android.activity.BaseListActivity;
import com.nature.elt.android.util.TextUtil;
import com.nature.elt.android.view.ExcelView;
import com.nature.elt.android.view.SearchBar;
import com.nature.elt.android.view.Selector;
import com.nature.elt.index.manager.KlineManager;
import com.nature.elt.common.manager.WorkDayManager;
import com.nature.elt.index.model.Kline;
import com.nature.elt.common.util.CommonUtil;
import com.nature.elt.common.util.InstanceHolder;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * K线列表
 * @author nature
 * @version 1.0.0
 * @since 2020/11/24 19:10
 */
public class KlineListActivity extends BaseListActivity<Kline> {

    private final KlineManager klineManager = InstanceHolder.get(KlineManager.class);
    private final WorkDayManager workDayManager = InstanceHolder.get(WorkDayManager.class);
    private final List<ExcelView.D<Kline>> ds = Arrays.asList(
            new ExcelView.D<>("名称", d -> TextUtil.text(d.getName()), C, S, CommonUtil.nullsLast(Kline::getName), getConsumer()),
            new ExcelView.D<>("CODE", d -> TextUtil.text(d.getCode()), C, S, CommonUtil.nullsLast(Kline::getCode)),
            new ExcelView.D<>("日期", d -> TextUtil.text(d.getDate()), C, S, CommonUtil.nullsLast(Kline::getDate)),
            new ExcelView.D<>("最新", d -> TextUtil.net(d.getLatest()), C, E, CommonUtil.nullsLast(Kline::getLatest)),
            new ExcelView.D<>("今开", d -> TextUtil.net(d.getOpen()), C, E, CommonUtil.nullsLast(Kline::getOpen)),
            new ExcelView.D<>("最高", d -> TextUtil.net(d.getHigh()), C, E, CommonUtil.nullsLast(Kline::getHigh)),
            new ExcelView.D<>("最低", d -> TextUtil.net(d.getLow()), C, E, CommonUtil.nullsLast(Kline::getLow)),
            new ExcelView.D<>("周平均", d -> TextUtil.net(d.getAvgWeek()), C, E, CommonUtil.nullsLast(Kline::getAvgWeek)),
            new ExcelView.D<>("月平均", d -> TextUtil.net(d.getAvgMonth()), C, E, CommonUtil.nullsLast(Kline::getAvgMonth)),
            new ExcelView.D<>("季平均", d -> TextUtil.net(d.getAvgSeason()), C, E, CommonUtil.nullsLast(Kline::getAvgSeason)),
            new ExcelView.D<>("年平均", d -> TextUtil.net(d.getAvgYear()), C, E, CommonUtil.nullsLast(Kline::getAvgYear))
    );
    private Selector<String> selector;
    private EditText editText;

    private Consumer<Kline> getConsumer() {
        return d -> {
            Intent intent = new Intent(getApplicationContext(), KlineActivity.class);
            intent.putExtra("code", d.getCode());
            intent.putExtra("market", d.getMarket());
            this.startActivity(intent);
        };
    }

    @Override
    protected List<ExcelView.D<Kline>> define() {
        return ds;
    }

    @Override
    protected List<Kline> listData() {
        String date = this.selector.getValue();
        String keyWord = this.editText.getText().toString();
        return klineManager.listByDate(date, keyWord);
    }

    @Override
    protected void initHeaderViews(SearchBar searchBar) {
        searchBar.addConditionView(selector = template.selector(100, 30));
        searchBar.addConditionView(editText = template.editText(100, 30));
    }

    @Override
    protected void initHeaderBehaviours() {
        selector.mapper(s -> s).init().refreshData(workDayManager.listWorkDays(workDayManager.getLatestWorkDay()));
    }

}
