package com.nature.kline.android.activity;

import android.content.Intent;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.nature.kline.android.util.PopUtil;
import com.nature.kline.android.util.TextUtil;
import com.nature.kline.android.view.ExcelView;
import com.nature.kline.android.view.SearchBar;
import com.nature.kline.android.view.Selector;
import com.nature.kline.common.enums.DefaultGroup;
import com.nature.kline.common.manager.GroupManager;
import com.nature.kline.common.manager.ItemManager;
import com.nature.kline.common.manager.PriceNetManager;
import com.nature.kline.common.manager.WorkDayManager;
import com.nature.kline.common.model.Group;
import com.nature.kline.common.model.Item;
import com.nature.kline.common.model.PriceNet;
import com.nature.kline.common.util.InstanceHolder;
import com.nature.kline.common.util.Sorter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 价格净值
 * @author nature
 * @version 1.0.0
 * @since 2020/11/24 18:54
 */
public class PriceNetListActivity extends BaseListActivity<PriceNet> {

    private Selector<String> selector;
    private Selector<Group> groupSel;
    private EditText editText;
    private LinearLayout window;

    private final PriceNetManager priceNetManager = InstanceHolder.get(PriceNetManager.class);

    private final GroupManager groupManager = InstanceHolder.get(GroupManager.class);

    private final WorkDayManager workDayManager = InstanceHolder.get(WorkDayManager.class);

    private final ItemManager itemManager = InstanceHolder.get(ItemManager.class);

    private Map<String, String> codeToMarket;

    private final List<ExcelView.D<PriceNet>> ds = Arrays.asList(
            new ExcelView.D<>("名称", d -> TextUtil.text(d.getName()), C, S, Sorter.nullsLast(PriceNet::getName), view()),
            new ExcelView.D<>("CODE", d -> TextUtil.text(d.getCode()), C, C, Sorter.nullsLast(PriceNet::getCode), kline()),
            new ExcelView.D<>("折价率", d -> TextUtil.hundred(d.getRateDiff()), C, E, Sorter.nullsLast(PriceNet::getRateDiff), detail()),
            new ExcelView.D<>("交易额", d -> TextUtil.amount(d.getAmount()), C, E, Sorter.nullsLast(PriceNet::getAmount)),
            new ExcelView.D<>("价格最高", d -> TextUtil.net(d.getPriceHigh()), C, E, Sorter.nullsLast(PriceNet::getPriceHigh)),
            new ExcelView.D<>("价格最低", d -> TextUtil.net(d.getPriceLow()), C, E, Sorter.nullsLast(PriceNet::getPriceLow)),
            new ExcelView.D<>("价格昨收", d -> TextUtil.net(d.getPriceLast()), C, E, Sorter.nullsLast(PriceNet::getPriceLast)),
            new ExcelView.D<>("价格最新", d -> TextUtil.net(d.getPriceLatest()), C, E, Sorter.nullsLast(PriceNet::getPriceLatest)),
            new ExcelView.D<>("价格增长", d -> TextUtil.hundred(d.getRatePrice()), C, E, Sorter.nullsLast(PriceNet::getRatePrice)),
            new ExcelView.D<>("净值昨收", d -> TextUtil.net(d.getNetLast()), C, E, Sorter.nullsLast(PriceNet::getNetLast)),
            new ExcelView.D<>("净值最新", d -> TextUtil.net(d.getNetLatest()), C, E, Sorter.nullsLast(PriceNet::getNetLatest)),
            new ExcelView.D<>("净值增长", d -> TextUtil.hundred(d.getRateNet()), C, E, Sorter.nullsLast(PriceNet::getRateNet)),
            new ExcelView.D<>("规模", d -> TextUtil.amount(d.getScale()), C, E, Sorter.nullsLast(PriceNet::getScale)),
            new ExcelView.D<>("交易额占比", d -> TextUtil.hundred(d.getRateAmount()), C, E, Sorter.nullsLast(PriceNet::getRateAmount))
    );

    private List<Group> getGroups() {
        List<Group> list = groupManager.list(DefaultGroup.FUND.getCode());
        Group group = new Group();
        group.setName("--请选择--");
        list.add(0, group);
        return list;
    }

    @Override
    protected List<ExcelView.D<PriceNet>> define() {
        return ds;
    }

    protected List<PriceNet> listData() {
        Group group = this.groupSel.getValue();
        String date = this.selector.getValue();
        String keyword = this.editText.getText().toString();
        String today = workDayManager.getToday();
        return today.equals(date) ?
                priceNetManager.listLatest(group, date, keyword) : priceNetManager.list(group, date, keyword);
    }

    @Override
    protected void initHeaderViews(SearchBar searchBar) {
        searchBar.addConditionView(groupSel = template.selector(100, 30));
        searchBar.addConditionView(selector = template.selector(100, 30));
        searchBar.addConditionView(editText = template.editText(100, 30));
    }

    @Override
    protected void initHeaderBehaviours() {
        selector.mapper(s -> s).init().refreshData(workDayManager.listWorkDays(workDayManager.getLatestWorkDay()));
        groupSel.mapper(Group::getName).init().refreshData(this.getGroups());
    }

    private void showDetail(PriceNet priceNet) {
        window = template.linearPage();
        window.setOrientation(LinearLayout.VERTICAL);
        window.setGravity(Gravity.TOP | Gravity.CENTER);
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
        window.addView(l1);
        window.addView(l2);
        window.addView(l3);
        window.addView(l4);
        window.addView(l5);
        window.addView(l6);
    }

    private Consumer<PriceNet> detail() {
        return d -> {
            this.showDetail(d);
            PopUtil.confirm(context, "详情", window, () -> {
            });
        };
    }

    private Consumer<PriceNet> view() {
        return d -> {
            Intent intent = new Intent(context, PriceNetActivity.class);
            intent.putExtra("code", d.getCode());
            this.startActivity(intent);
        };
    }

    private Consumer<PriceNet> kline() {
        return d -> {
            Intent intent = new Intent(context, KlineActivity.class);
            intent.putExtra("market", this.getMarket(d.getCode()));
            intent.putExtra("code", d.getCode());
            this.startActivity(intent);
        };
    }

    private String getMarket(String code) {
        if (codeToMarket == null)
            codeToMarket = itemManager.list().stream().collect(Collectors.toMap(Item::getCode, Item::getMarket));
        return codeToMarket.get(code);
    }
}
