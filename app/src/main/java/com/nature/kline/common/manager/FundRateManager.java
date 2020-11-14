package com.nature.kline.common.manager;

import com.alibaba.fastjson.JSON;
import com.nature.kline.common.ioc.annotation.Injection;
import com.nature.kline.common.mapper.FundRateMapper;
import com.nature.kline.common.model.*;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 基金增长率
 * @author nature
 * @version 1.0.0
 * @since 2020/9/20 15:11
 */
public class FundRateManager {

    @Injection
    private FundRateMapper fundRateMapper;
    @Injection
    private FundListDefManager fundListDefManager;
    @Injection
    private NetManager netManager;
    @Injection
    private ItemManager itemManager;
    @Injection
    private ScaleManager scaleManager;

    public List<FundRate> list(Definition def, Group group, String date, String keyword) {
        List<FundRate> list = fundRateMapper.listByDate(date);
        if (list.isEmpty()) return list;
        Map<String, String> items = itemManager.list().stream().collect(Collectors.toMap(Item::getCode, Item::getName));
        Map<String, Double> scales = scaleManager.listLast(date).stream()
                .collect(Collectors.toMap(Scale::getCode, Scale::getAmount));
        list = list.stream().peek(i -> {
            i.setName(items.get(i.getCode()));
            i.setScale(scales.get(i.getCode()));
        }).filter(i -> doFilter(i, keyword)).collect(Collectors.toList());
        if (group != null && group.getCodes() != null)
            list = list.stream().filter(i -> group.getCodes().contains(i.getCode())).collect(Collectors.toList());
        if (def == null) return list;
        String json = def.getJson();
        if (StringUtils.isBlank(json)) return list;
        List<FundListDef> ds = JSON.parseArray(json, FundListDef.class);
        if (ds == null || ds.isEmpty()) return list;
        ds.forEach(i -> fundListDefManager.calculateDate(i, date));
        Set<String> dates = new TreeSet<>();
        ds.forEach(i -> {
            dates.add(i.getDateStart());
            dates.add(i.getDateEnd());
        });
        Map<String, Double> nets = new HashMap<>();
        dates.parallelStream().forEach(d -> this.appendNets(nets, d));
        for (FundRate rate : list) for (FundListDef d : ds) this.formatFundRate(rate, d, nets);
        return list;
    }

    private void appendNets(Map<String, Double> nets, String d) {
        if (StringUtils.isBlank(d)) return;
        List<Net> ns = netManager.listLast(d);
        nets.putAll(ns.stream().collect(Collectors.toMap(i -> this.k(i.getCode(), d), Net::getNetTotal)));
    }

    private void formatFundRate(FundRate rate, FundListDef d, Map<String, Double> nets) {
        String code = rate.getCode();
        String dateStart = d.getDateStart();
        String dateEnd = d.getDateEnd();
        Double netStart = nets.get(this.k(code, dateStart));
        Double netEnd = nets.get(this.k(code, dateEnd));
        if (StringUtils.isBlank(dateEnd)) netEnd = rate.getNetTotal();   // 兼容处理xx日期至今的情况
        if (netStart == null || netEnd == null) return;
        rate.putRate(d.getCode(), (netEnd - netStart) / netStart);
    }

    private boolean doFilter(FundRate rate, String keyword) {
        return rate.getName() != null && (rate.getCode().contains(keyword) || rate.getName().contains(keyword));
    }

    private String k(String code, String date) {
        return String.join(":", code, date);
    }
}
