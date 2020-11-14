package com.nature.kline.common.mapper;


import android.database.Cursor;
import com.nature.kline.android.db.BaseDB;
import com.nature.kline.android.db.SqlParam;
import com.nature.kline.common.model.TaskRecord;

import java.util.List;
import java.util.function.Function;

/**
 * 任务记录
 * @author nature
 * @version 1.0.0
 * @since 2020/2/23 21:42
 */
public class TaskRecordMapper {

    private final BaseDB baseDB = BaseDB.create();

    public TaskRecordMapper() {
        baseDB.executeSql("" +
                "CREATE TABLE IF NOT EXISTS task_record ( " +
                " code TEXT NOT NULL, " +
                " date TEXT NOT NULL, " +
                " start_time TEXT NULL, " +
                " end_time TEXT NULL, " +
                " status TEXT NOT NULL, " +
                " exception TEXT NULL, " +
                " PRIMARY KEY (code, date) " +
                ")");
    }

    private static final Function<Cursor, TaskRecord> resultMapper = c -> {
        TaskRecord t = new TaskRecord();
        t.setCode(BaseDB.getString(c, "code"));
        t.setDate(BaseDB.getString(c, "date"));
        t.setStartTime(BaseDB.getString(c, "start_time"));
        t.setEndTime(BaseDB.getString(c, "end_time"));
        t.setStatus(BaseDB.getString(c, "status"));
        t.setException(BaseDB.getString(c, "exception"));
        return t;
    };

    /**
     * 查询任务记录
     * @return TaskRecord
     */
    public TaskRecord get(String code, String date) {
        SqlParam sql = SqlParam.build().append("select code, date, start_time, end_time, status, exception")
                .append("from task_record where code = ? and date = ?", code, date);
        return baseDB.find(sql, resultMapper);
    }

    /**
     * 查询任务记录
     * @param date date
     * @return list
     */
    public List<TaskRecord> list(String date) {
        SqlParam sql = SqlParam.build().append("select code, date, start_time, end_time, status, exception")
                .append("from task_record");
        if (date != null) sql.append("where date = ?", date);
        sql.append("order by date desc, start_time");
        return baseDB.list(sql, resultMapper);
    }

    public int merge(TaskRecord d) {
        SqlParam sql = SqlParam.build().append("replace into task_record(code, date, start_time, end_time, status")
                .append(", exception) values(?, ?, ?, ?, ?, ?)", d.getCode(), d.getDate(), d.getStartTime(),
                        d.getEndTime(), d.getStatus(), d.getException());
        return baseDB.executeUpdate(sql);
    }

}
