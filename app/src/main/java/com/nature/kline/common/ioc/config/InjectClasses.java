package com.nature.kline.common.ioc.config;

import com.nature.kline.common.manager.*;

/**
 * 需要进行注入操作的类
 * @author nature
 * @version 1.0.0
 * @since 2019/11/23 19:38
 */
public interface InjectClasses {

    Class<?>[] CLASSES = new Class[]{
            WorkDayManager.class,
            TaskManager.class,
            KlineManager.class,
            ItemManager.class,
            GroupManager.class,
            ItemGroupManager.class,
            PriceNetManager.class,
            NetManager.class,
            QuotaManager.class,
            LineManager.class,
            StrategyManager.class,
            ScaleManager.class,
            DefinitionManager.class,
            FundListDefManager.class,
            FundRateManager.class,
            ItemQuotaManager.class,
            MarkManager.class,
            TargetManager.class
    };
}
