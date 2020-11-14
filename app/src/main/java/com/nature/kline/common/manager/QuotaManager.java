package com.nature.kline.common.manager;

import com.nature.kline.android.db.BaseDB;
import com.nature.kline.common.http.QuotaHttp;
import com.nature.kline.common.ioc.annotation.Injection;
import com.nature.kline.common.ioc.annotation.TaskMethod;
import com.nature.kline.common.mapper.QuotaMapper;
import com.nature.kline.common.model.Quota;
import com.nature.kline.common.util.ExeUtil;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * 净值
 * @author nature
 * @version 1.0.0
 * @since 2020/8/19 19:18
 */
public class QuotaManager {

    @Injection
    private QuotaMapper quotaMapper;

    @Injection
    private QuotaHttp quotaHttp;

    @Injection
    private WorkDayManager workDayManager;

    public int loadAll() {
        return ExeUtil.exec(quotaMapper::delete, this::codes, this::load);
    }

    @TaskMethod("load_latest_quota")
    public int loadLatest() {
        return ExeUtil.exec(this::codes, this::load);
    }

    private int load(String code) {
        // 查询已有的第一条数据
        Quota first = quotaMapper.findFirstByCode(code), last = quotaMapper.findLastByCode(code);
        String dateStart = last == null ? "1999-12-31" : last.getDate();
        List<Quota> list = quotaHttp.list(code, dateStart);
        if (list.isEmpty()) return 0;
        this.calculateRate(list, first == null ? list.get(0) : first);
        return this.batchMerge(list);
    }

    public int batchMerge(List<Quota> list) {
        if (list == null || list.isEmpty()) return 0;
        return BaseDB.create().batchExec(list, 50, l -> quotaMapper.batchMerge(l));
    }

    private void calculateRate(List<Quota> list, Quota quota) {
        for (Quota q : list) {
            q.setSylRate(this.rate(q::getSyl, quota::getSyl));
            q.setPriceRate(this.rate(q::getPrice, quota::getPrice));
            q.setCountRate(this.rate(q::getCount, quota::getCount));
            q.setSzZRate(this.rate(q::getSzZ, quota::getSzZ));
            q.setGbZRate(this.rate(q::getGbZ, quota::getGbZ));
            q.setSzLtRate(this.rate(q::getSzLt, quota::getSzLt));
            q.setGbLtRate(this.rate(q::getGbLt, quota::getGbLt));
        }
    }

    private double rate(Supplier<Double> a, Supplier<Double> b) {
        Double ad = a.get(), bd = b.get();
        return (ad - bd) / bd;
    }

    public List<Quota> listByCode(String code) {
        return quotaMapper.listByCode(code);
    }

    private List<String> codes() {
        return Arrays.asList("000001", "399001", "000300", "399005", "399006");
    }

}
