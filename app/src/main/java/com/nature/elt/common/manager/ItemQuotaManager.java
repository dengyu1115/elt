package com.nature.elt.common.manager;

import com.nature.elt.common.enums.DefaultGroup;
import com.nature.elt.common.ioc.annotation.Injection;
import com.nature.elt.common.model.*;
import com.nature.elt.common.util.CalUtil;
import com.nature.elt.item.manager.ItemGroupManager;
import com.nature.elt.item.model.Group;
import com.nature.elt.item.model.Item;
import com.nature.elt.index.manager.KlineManager;
import com.nature.elt.index.model.Kline;
import com.nature.elt.fund.manager.NetManager;
import com.nature.elt.fund.model.Net;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 项目指标
 * @author nature
 * @version 1.0.0
 * @since 2020/11/28 14:14
 */
public class ItemQuotaManager {

    @Injection
    private KlineManager klineManager;
    @Injection
    private ItemGroupManager itemGroupManager;
    @Injection
    private NetManager netManager;

    public List<ItemQuota> list(Group group, String keyword, String dateStart, String dateEnd) {
        List<Item> list = itemGroupManager.listItem(group.getCode()).stream()
                .filter(i -> i.getName().contains(keyword) || i.getCode().contains(keyword))
                .collect(Collectors.toList());
        if (list.isEmpty()) return new ArrayList<>();
        if (DefaultGroup.INDEX.getCode().equals(group.getCode())) {
            return list.parallelStream().map(i -> this.calculate(
                    i,
                    dateStart,
                    Kline::getDate,
                    () -> klineManager.list(i.getCode(), i.getMarket(), dateStart, dateEnd),
                    () -> klineManager.findLast(i.getCode(), i.getMarket(), dateStart),
                    Kline::getLatest,
                    Kline::getLow,
                    Kline::getHigh)).filter(Objects::nonNull).collect(Collectors.toList());
        } else {
            return list.parallelStream().map(i -> this.calculate(
                    i,
                    dateStart,
                    Net::getDate,
                    () -> netManager.list(i.getCode(), dateStart, dateEnd),
                    () -> netManager.findLast(i.getCode(), dateStart),
                    Net::getNetTotal,
                    Net::getNetTotal,
                    Net::getNetTotal)).filter(Objects::nonNull).collect(Collectors.toList());
        }
    }

    public <T> ItemQuota calculate(Item item, String dateStart, Function<T, String> getDate,
                                   Supplier<List<T>> getList, Supplier<T> getFirst,
                                   Function<T, Double> getPrice,
                                   Function<T, Double> getLow, Function<T, Double> getHigh) {
        List<T> ks = getList.get();
        if (ks.isEmpty()) return null;
        T first = getFirst.get();
        if (first == null && !dateStart.equals(getDate.apply(ks.get(0)))) return null;
        if (first == null) first = ks.get(0);
        ItemQuota quota = this.itemToQuota(item);
        double open = getPrice.apply(first), low = open, high = open, total = 0d, latest = getPrice.apply(ks.get(ks.size() - 1));
        for (T k : ks) {
            if (getLow.apply(k) < low) low = getLow.apply(k);
            if (getHigh.apply(k) > high) high = getHigh.apply(k);
            total += getPrice.apply(k);
        }
        double avg = total / (double) ks.size();
        double rateOpen = (latest - open) / open;
        double rateHigh = (latest - high) / high;
        double rateLow = (latest - low) / low;
        double rateAvg = (latest - avg) / avg;
        double rateLH = CalUtil.maxUp(ks, getPrice);
        double rateHL = CalUtil.maxDown(ks, getPrice);
        double ratioLow = (high - low) == 0 ? 1d : (latest - low) / (high - low);
        double ratioAvg = (high - low) == 0 ? 1d : (avg - low) / (high - low);
        quota.setDateStart(getDate.apply(ks.get(0)));
        quota.setDateEnd(getDate.apply(ks.get(ks.size() - 1)));
        quota.setOpen(open);
        quota.setHigh(high);
        quota.setLow(low);
        quota.setLatest(latest);
        quota.setAvg(avg);
        quota.setRateOpen(rateOpen);
        quota.setRateHigh(rateHigh);
        quota.setRateLow(rateLow);
        quota.setRateAvg(rateAvg);
        quota.setRateLH(rateLH);
        quota.setRateHL(rateHL);
        quota.setRatioLatest(ratioLow);
        quota.setRatioAvg(ratioAvg);
        return quota;
    }

    private ItemQuota itemToQuota(Item item) {
        ItemQuota quota = new ItemQuota();
        quota.setCode(item.getCode());
        quota.setName(item.getName());
        quota.setMarket(item.getMarket());
        quota.setType(item.getType());
        return quota;
    }
}
