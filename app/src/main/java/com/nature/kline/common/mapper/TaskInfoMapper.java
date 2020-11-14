package com.nature.kline.common.mapper;


import android.database.Cursor;
import com.nature.kline.android.db.BaseDB;
import com.nature.kline.android.db.SqlParam;
import com.nature.kline.common.model.TaskInfo;

import java.util.List;
import java.util.function.Function;

/**
 * 任务信息
 * @author nature
 * @version 1.0.0
 * @since 2020/2/23 21:42
 */
public class TaskInfoMapper {

    private static final String SQL = "select code, type, start_time, end_time, last, remark from task_info";
    private static final String SQL_TABLE = "" +
            "CREATE TABLE IF NOT EXISTS task_info (" +
            " code TEXT NOT NULL," +
            " type TEXT NOT NULL," +
            " start_time TEXT NOT NULL," +
            " end_time TEXT NOT NULL," +
            " last TEXT NULL," +
            " remark TEXT NULL," +
            " PRIMARY KEY (code)" +
            ")";

    private final BaseDB baseDB = BaseDB.create();

    public TaskInfoMapper() {
        baseDB.executeSql(SQL_TABLE);
    }

    private static final Function<Cursor, TaskInfo> resultMapper = c -> {
        TaskInfo t = new TaskInfo();
        t.setCode(BaseDB.getString(c, "code"));
        t.setType(BaseDB.getString(c, "type"));
        t.setStartTime(BaseDB.getString(c, "start_time"));
        t.setEndTime(BaseDB.getString(c, "end_time"));
        t.setLast(BaseDB.getString(c, "last"));
        t.setRemark(BaseDB.getString(c, "remark"));
        return t;
    };

    /**
     * 查询全部任务信息
     * @return list
     */
    public List<TaskInfo> list() {
        return baseDB.list(SqlParam.build().append(SQL), resultMapper);
    }

}
