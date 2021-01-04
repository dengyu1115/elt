package com.nature.elt.common.ioc.config;

import com.nature.elt.common.manager.*;
import com.nature.elt.fund.manager.FundListDefManager;
import com.nature.elt.fund.manager.FundRateManager;
import com.nature.elt.fund.manager.ScaleManager;
import com.nature.elt.index.manager.QuotaManager;
import com.nature.elt.item.manager.GroupManager;
import com.nature.elt.item.manager.ItemGroupManager;
import com.nature.elt.item.manager.ItemManager;
import com.nature.elt.index.manager.KlineManager;
import com.nature.elt.fund.manager.NetManager;

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
            TargetManager.class,
            TaskInfoManager.class
    };
}
