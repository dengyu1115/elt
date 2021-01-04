package com.nature.elt.fund.mapper;


import android.database.Cursor;
import com.nature.elt.android.db.BaseDB;
import com.nature.elt.android.db.SqlParam;
import com.nature.elt.fund.model.FundRate;

import java.util.List;
import java.util.function.Function;

/**
 * 价格净值mapper
 * @author nature
 * @version 1.0.0
 * @since 2020/8/8 15:47
 */
public class FundRateMapper {

    private final BaseDB baseDB = BaseDB.create();

    private final Function<Cursor, FundRate> mapper = c -> {
        FundRate i = new FundRate();
        i.setCode(BaseDB.getString(c, "code"));
        i.setDate(BaseDB.getString(c, "date"));
        i.setNet(BaseDB.getDouble(c, "net"));
        i.setRate(BaseDB.getDouble(c, "rate"));
        i.setNetTotal(BaseDB.getDouble(c, "net_total"));
        i.setRateTotal(BaseDB.getDouble(c, "rate_total"));
        return i;
    };

    public List<FundRate> listByDate(String date) {
        SqlParam param = SqlParam.build().append("select t1.* from (select code, ? date from net group by code)" +
                " t0 join net t1 on t0.code = t1.code and t0.date = t1.date", date);
        return baseDB.list(param, mapper);
    }

}
