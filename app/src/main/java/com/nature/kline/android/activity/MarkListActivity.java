package com.nature.kline.android.activity;

import android.content.Intent;
import android.widget.EditText;
import com.alibaba.fastjson.JSON;
import com.nature.kline.android.util.PopUtil;
import com.nature.kline.android.util.TextUtil;
import com.nature.kline.android.view.ExcelView;
import com.nature.kline.android.view.SearchBar;
import com.nature.kline.common.manager.ItemManager;
import com.nature.kline.common.manager.MarkManager;
import com.nature.kline.common.model.Item;
import com.nature.kline.common.model.Mark;
import com.nature.kline.common.util.CommonUtil;
import com.nature.kline.common.util.InstanceHolder;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 交易
 * @author nature
 * @version 1.0.0
 * @since 2020/11/7 9:55
 */
public class MarkListActivity extends ListPageActivity<Mark> {

    private EditText keyword;

    private final MarkManager markManager = InstanceHolder.get(MarkManager.class);

    private final ItemManager itemManager = InstanceHolder.get(ItemManager.class);

    private Map<String, String> itemMap;

    private final List<ExcelView.D<Mark>> ds = Arrays.asList(
            new ExcelView.D<>("名称", d -> TextUtil.text(d.getName()), C, S, CommonUtil.nullsLast(Mark::getName), this.detail()),
            new ExcelView.D<>("code", d -> TextUtil.text(d.getCode()), C, C, CommonUtil.nullsLast(Mark::getCode)),
            new ExcelView.D<>("market", d -> TextUtil.text(d.getMarket()), C, C, CommonUtil.nullsLast(Mark::getMarket)),
            new ExcelView.D<>("日期", d -> TextUtil.text(d.getDate()), C, C, CommonUtil.nullsLast(Mark::getDate)),
            new ExcelView.D<>("价格", d -> TextUtil.net(d.getPrice()), C, E, CommonUtil.nullsLast(Mark::getPrice)),
            new ExcelView.D<>("补仓跌幅", d -> TextUtil.hundred(d.getRateBuy()), C, E, CommonUtil.nullsLast(Mark::getRateBuy)),
            new ExcelView.D<>("补仓价格", d -> TextUtil.net(d.getPriceBuy()), C, E, CommonUtil.nullsLast(Mark::getPriceBuy)),
            new ExcelView.D<>("止盈涨幅", d -> TextUtil.hundred(d.getRateSell()), C, E, CommonUtil.nullsLast(Mark::getRateSell)),
            new ExcelView.D<>("止盈价格", d -> TextUtil.net(d.getPriceSell()), C, E, CommonUtil.nullsLast(Mark::getPriceSell)),
            new ExcelView.D<>("操作", d -> "-", C, C, CommonUtil.nullsLast(Mark::getRateSell), this.delete())
    );

    @Override
    protected List<ExcelView.D<Mark>> define() {
        return ds;
    }

    @Override
    protected List<Mark> listData() {
        Mark mark = this.getInitMark();
        List<Mark> list;
        if (mark == null) {
            String keyword = this.keyword.getText().toString();
            list = markManager.list();
            Stream<Mark> stream = list.stream().peek(i -> i.setName(itemMap.get(this.key(i.getCode(), i.getMarket()))));
            if (StringUtils.isNotBlank(keyword)) {
                stream = stream.filter(i -> i.getName().contains(keyword) || i.getCode().contains(keyword));
            }
            list = stream.collect(Collectors.toList());
        } else {
            list = markManager.list(mark.getCode(), mark.getMarket());
            list.forEach(i -> i.setName(itemMap.get(this.key(i.getCode(), i.getMarket()))));
        }
        return list;
    }


    @Override
    protected void initHeaderViews(SearchBar searchBar) {
        if (this.getInitMark() == null) {
            searchBar.addConditionView(keyword = template.editText(100, 30));
        }
    }

    @Override
    protected void initHeaderBehaviours() {
        List<Item> items = itemManager.list();
        itemMap = items.stream().collect(Collectors.toMap(i -> this.key(i.getCode(), i.getMarket()), Item::getName));
    }

    private Consumer<Mark> detail() {
        return d -> {
            if (this.getInitMark() == null) {
                Intent intent = new Intent(context, MarkListActivity.class);
                intent.putExtra("mark", JSON.toJSONString(d));
                this.startActivity(intent);
            }
        };
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

    private Mark getInitMark() {
        return JSON.parseObject(this.getIntent().getStringExtra("mark"), Mark.class);
    }

}
