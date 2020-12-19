package com.nature.elt.common.manager;

import com.nature.elt.common.ioc.annotation.Injection;
import com.nature.elt.common.mapper.StrategyMapper;
import com.nature.elt.common.model.Strategy;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * strategy
 * @author nature
 * @version 1.0.0
 * @since 2020/4/19 11:35
 */
public class StrategyManager {

    private static final int BATCH_SIZE = 200;

    @Injection
    private StrategyMapper strategyMapper;

    /**
     * 批量保存
     * @param d Strategy
     * @return int
     */
    public int merge(Strategy d) {
        return strategyMapper.merge(d);
    }

    /**
     * 查询
     * @return list
     */
    public List<Strategy> list() {
        return strategyMapper.list();
    }

    public int delete(String code) {
        return strategyMapper.delete(code);
    }

    public Strategy findByCode(String code) {
        if (StringUtils.isBlank(code)) return null;
        return strategyMapper.findByCode(code);
    }
}
