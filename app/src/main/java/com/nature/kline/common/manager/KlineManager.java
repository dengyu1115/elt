package com.nature.kline.common.manager;

import com.nature.kline.android.db.BaseDB;
import com.nature.kline.common.constant.Constant;
import com.nature.kline.common.http.KlineHttp;
import com.nature.kline.common.ioc.annotation.Injection;
import com.nature.kline.common.ioc.annotation.TaskMethod;
import com.nature.kline.common.mapper.KlineMapper;
import com.nature.kline.common.model.Item;
import com.nature.kline.common.model.Kline;
import com.nature.kline.common.util.CommonUtil;
import com.nature.kline.common.util.ExeUtil;
import com.nature.kline.common.util.MaUtil;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * kline manager
 * @author nature
 * @version 1.0.0
 * @since 2020/4/19 11:12
 */
public class KlineManager {

    private static final int WEEK = 5, MONTH = 21, SEASON = 63, YEAR = 252;

    private static final int BATCH_SIZE = 70;
    @Injection
    private KlineHttp klineHttp;
    @Injection
    private KlineMapper klineMapper;
    @Injection
    private ItemManager itemManager;
    @Injection
    private ItemQuotaManager itemQuotaManager;
    @Injection
    private ItemGroupManager itemGroupManager;
    @Injection
    private WorkDayManager workDayManager;

    private Map<String, Kline> map;

    /**
     * 加载全部数据
     * @return int
     */
    public int reloadAll() {
        return ExeUtil.exec(klineMapper::delete, this::listItems, this::reload);
    }

    /**
     * 加载最新数据
     * @return int
     */
    @TaskMethod("load_latest_kline")
    public int loadLatest() {
        return workDayManager.doInTradeTimeOrNot(date -> {
            throw new RuntimeException("交易时间不可同步数据");
        }, date -> ExeUtil.exec(
                () -> map = klineMapper.listLast().stream().collect(Collectors.toMap(Kline::getCode, i -> i)),
                this::listItems, this::doLoad, () -> map = null));
    }

    /**
     * 计算平均值
     * @return int
     */
    @TaskMethod("calculate_kline_average")
    public int averageAll() {
        return ExeUtil.exec(this::listItems, this::average);
    }

    /**
     * 按照code，type查询k线数据
     * @param code   code
     * @param market market
     * @return list
     */
    public List<Kline> list(String code, String market) {
        return klineMapper.list(code, market);
    }

    public List<Kline> list(String code, String market, String dateStart, String dateEnd) {
        return klineMapper.list(code, market, dateStart, dateEnd);
    }

    /**
     * 按照date，keyWord查询k线数据
     * @param date    日期
     * @param keyWord 关键字
     * @return list
     */
    public List<Kline> listByDate(String date, String keyWord) {
        List<Kline> list = klineMapper.listByDate(date);
        Map<String, String> map = itemManager.list().stream().collect(Collectors.toMap(this::key, Item::getName));
        if (keyWord != null && !keyWord.isEmpty()) {
            list = list.stream().filter(k -> {
                k.setName(map.get(this.key(k)));
                return k.getCode().contains(keyWord) || k.getName().contains(keyWord);
            }).collect(Collectors.toList());
        } else {
            for (Kline k : list) k.setName(map.get(this.key(k)));
        }
        return list;
    }

    /**
     * 按照date，keyWord查询k线数据
     * @param date 日期
     * @return list
     */
    public List<Kline> listByDate(String date) {
        return klineMapper.listByDate(date);
    }

    /**
     * 按照date，keyWord查询k线数据
     * @param date     日期
     * @param strategy 策略
     * @return list
     */
    public List<Kline> listByStrategy(String date, String strategy) {
        List<Kline> list = klineMapper.listByDate(date);
        Map<String, String> map = itemManager.list().stream().collect(Collectors.toMap(this::key, Item::getName));
        for (Kline k : list) k.setName(map.get(this.key(k)));
        switch (strategy) {
            case "1":
                list = list.stream().filter(k -> {
                    Double latest = k.getLatest();
                    Double avgWeek = k.getAvgWeek();
                    Double avgMonth = k.getAvgMonth();
                    Double avgYear = k.getAvgYear();
                    return avgYear != null && avgMonth != null
                            && avgWeek != null && latest < avgYear && latest > avgWeek && latest < avgMonth;
                }).collect(Collectors.toList());
                break;
            case "2":
                list = list.stream().filter(k -> {
                    Double latest = k.getLatest();
                    Double avgWeek = k.getAvgWeek();
                    Double avgMonth = k.getAvgMonth();
                    Double avgYear = k.getAvgYear();
                    return avgYear != null && avgMonth != null
                            && avgWeek != null && latest > avgYear && latest < avgWeek && latest > avgMonth;
                }).collect(Collectors.toList());
                break;
        }
        return list;
    }

    private String key(Item i) {
        return String.join(":", i.getCode(), i.getMarket());
    }

    /**
     * 加载k线数据
     * @return int
     */
    public int doLoad(Item item) {
        String code = item.getCode();
        String market = item.getMarket();
        Kline kline = map.get(code);
        String start = this.getLastDate(kline), end = DateFormatUtils.format(new Date(), Constant.FORMAT_DAY);
        return this.batchMerge(klineHttp.list(code, market, start, end));
    }

    private String getLastDate(Kline kline) {
        return kline == null ? "" : CommonUtil.addDays(kline.getDate(), 1).replace("-", "");
    }

    /**
     * 加载k线数据
     * @param item item
     * @return int
     */
    public int reload(Item item) {
        String start = "", end = DateFormatUtils.format(new Date(), Constant.FORMAT_DAY);
        List<Kline> list = klineHttp.list(item.getCode(), item.getMarket(), start, end);
        return this.batchMerge(list);
    }

    /**
     * 批量merge
     * @param list list
     * @return int
     */
    public int batchMerge(List<Kline> list) {
        if (list == null || list.isEmpty()) return 0;
        return BaseDB.create().batchExec(list, BATCH_SIZE, klineMapper::batchMerge);
    }

    private int average(Item item) {
        List<Kline> ks = new ArrayList<>();
        List<Kline> rs = this.genResults(item, ks); // 结果数据集
        if (rs.isEmpty()) return 0;   // 适应数据算法，年、季、月、周
        this.calculate(ks, Kline::getAvgYear, Kline::setAvgYear, YEAR);
        this.calculate(ks, Kline::getAvgSeason, Kline::setAvgSeason, SEASON);
        this.calculate(ks, Kline::getAvgMonth, Kline::setAvgMonth, MONTH);
        this.calculate(ks, Kline::getAvgWeek, Kline::setAvgWeek, WEEK);
        this.batchMerge(rs);
        return rs.size();
    }

    private List<Kline> genResults(Item item, List<Kline> ks) {
        List<Kline> rs = new ArrayList<>();
        String date = null;
        int start = 0, limit = YEAR, index = 0;
        boolean finished = false;
        while (true) {
            if (limit <= 0) break; // 没有数据需要加载了
            List<Kline> list = klineMapper.listBefore(item.getCode(), item.getMarket(), date, start, limit);
            if (list.isEmpty()) break;  // 查不到更多数据
            ks.addAll(list);
            if (finished) break;    // 已经确认需要计算的位置
            int end = ks.size();
            while (index < end) {    // 所有的数据都为空则说明还未计算
                Kline k = ks.get(index);
                if (this.anyNotNull(k.getAvgWeek(), k.getAvgMonth(), k.getAvgSeason(), k.getAvgYear())) { // 当中有任意值有值说明已经计算过
                    limit = index - end + YEAR - 1; // 计算下一批取数条数
                    finished = true;
                    break;
                } else {   // 尚未计算的列入结果集
                    rs.add(k);
                }
                if (index == end - 1) break;    // 遍历完全，跳出循环
                else index++;   // 遍历下一条
            }
            date = ks.get(end - 1).getDate(); // 下一批数据查询条件设置
        }
        return rs;
    }

    private boolean anyNotNull(Double... ds) {
        for (Double d : ds) {
            if (d != null) return true;
        }
        return false;
    }

    /**
     * 计算平均值
     * @param ks    集合
     * @param get   取值
     * @param set   设值
     * @param limit 平均数量
     */
    private void calculate(List<Kline> ks, Function<Kline, Double> get, BiConsumer<Kline, Double> set, int limit) {
        int size = ks.size();
        for (int i = 0; i < size; i++) {
            Kline kline = ks.get(i);
            if (get.apply(kline) != null || i + limit > size) break; // 有值说明已经计算过
            set.accept(kline, MaUtil.average(ks.subList(i, i + limit), Kline::getLatest));
        }
    }

    private List<Item> listItems() {
        List<Item> list = itemGroupManager.listAllFunds();
        list.addAll(itemGroupManager.listAllIndexes());
        return list;
    }

    public List<Kline> listAfter(String code, String market, String date) {
        return klineMapper.listAfter(code, market, date);
    }

    public Kline findLast(String code, String market, String date) {
        return klineMapper.findLast(code, market, date);
    }
}