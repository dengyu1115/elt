package com.nature.kline.android.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.nature.kline.android.util.PopUtil;
import com.nature.kline.android.util.TextUtil;
import com.nature.kline.android.view.ExcelView;
import com.nature.kline.android.view.SearchBar;
import com.nature.kline.android.view.Selector;
import com.nature.kline.common.manager.ItemManager;
import com.nature.kline.common.manager.MarkManager;
import com.nature.kline.common.model.Item;
import com.nature.kline.common.model.Mark;
import com.nature.kline.common.util.CommonUtil;
import com.nature.kline.common.util.InstanceHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 交易
 * @author nature
 * @version 1.0.0
 * @since 2020/11/7 9:55
 */
public class MarkListActivity extends ListPageActivity<Mark> {

    private EditText keyword;
    private Selector<Mark> selector;

    private final MarkManager markManager = InstanceHolder.get(MarkManager.class);

    private final ItemManager itemManager = InstanceHolder.get(ItemManager.class);

    private Map<String, String> itemMap;

    private final List<ExcelView.D<Mark>> ds = Arrays.asList(
            new ExcelView.D<>("名称", d -> TextUtil.text(d.getName()), C, S, CommonUtil.nullsLast(Mark::getName)),
            new ExcelView.D<>("code", d -> TextUtil.text(d.getCode()), C, C, CommonUtil.nullsLast(Mark::getCode)),
            new ExcelView.D<>("market", d -> TextUtil.text(d.getMarket()), C, C, CommonUtil.nullsLast(Mark::getMarket)),
            new ExcelView.D<>("日期", d -> TextUtil.text(d.getDate()), C, C, CommonUtil.nullsLast(Mark::getDate)),
            new ExcelView.D<>("价格", d -> TextUtil.net(d.getPrice()), C, E, CommonUtil.nullsLast(Mark::getPrice)),
            new ExcelView.D<>("补仓跌幅", d -> TextUtil.hundred(d.getRateBuy()), C, E, CommonUtil.nullsLast(Mark::getRateBuy)),
            new ExcelView.D<>("补仓价格", d -> TextUtil.net(d.getPriceBuy()), C, E, CommonUtil.nullsLast(Mark::getPriceBuy)),
            new ExcelView.D<>("止盈涨幅", d -> TextUtil.hundred(d.getRateSell()), C, E, CommonUtil.nullsLast(Mark::getRateSell)),
            new ExcelView.D<>("止盈价格", d -> TextUtil.net(d.getPriceSell()), C, E, CommonUtil.nullsLast(Mark::getPriceSell)),
            new ExcelView.D<>("操作", d -> "-", C, C, CommonUtil.nullsLast(Mark::getRateSell), delete())
    );

    @Override
    protected List<ExcelView.D<Mark>> define() {
        return ds;
    }

    @Override
    protected List<Mark> listData() {
        Mark mark = this.selector.getValue();
        List<Mark> list;
        if (mark.getCode() == null) {
            list = markManager.list();
        } else {
            list = markManager.list(mark.getCode(), mark.getMarket());
        }
        list.forEach(i -> i.setName(itemMap.get(this.key(i.getCode(), i.getMarket()))));
        return list;
    }


    @Override
    protected void initHeaderViews(SearchBar searchBar) {
        searchBar.addConditionView(keyword = template.editText(100, 30));
        searchBar.addConditionView(selector = template.selector(100, 30));
    }

    @Override
    protected void initHeaderBehaviours() {
        List<Item> items = itemManager.list();
        itemMap = items.stream().collect(Collectors.toMap(i -> this.key(i.getCode(), i.getMarket()), Item::getName));
        List<Mark> list = markManager.distinct();
        list.forEach(i -> i.setName(itemMap.get(this.key(i.getCode(), i.getMarket()))));
        Mark mark = new Mark();
        mark.setName("--请选择--");
        list.add(0, mark);
        selector.mapper(Mark::getName).init().refreshData(list);
        keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                List<Mark> li = list.stream().filter(i -> i.getName().contains(keyword)).collect(Collectors.toList());
                selector.refreshData(li);
            }
        });
    }

    private Consumer<Mark> delete() {
        return d -> PopUtil.confirm(context, "删除数据", "确认删除吗？", () -> {
            markManager.delete(d.getCode(), d.getMarket(), d.getDate());
            this.refreshData();
            PopUtil.alert(context, "删除成功");
        });
    }

    private String key(String code, String market) {
        return String.join(":", code, market);
    }

}
