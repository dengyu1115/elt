package com.nature.kline.common.manager;

import com.nature.kline.common.constant.Constant;
import com.nature.kline.common.enums.ExeStatus;
import com.nature.kline.common.enums.TaskType;
import com.nature.kline.common.ioc.annotation.Injection;
import com.nature.kline.common.mapper.TaskInfoMapper;
import com.nature.kline.common.mapper.TaskRecordMapper;
import com.nature.kline.common.model.TaskInfo;
import com.nature.kline.common.model.TaskRecord;
import com.nature.kline.common.util.CommonUtil;
import com.nature.kline.common.util.TaskHolder;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 任务管理
 * @author nature
 * @version 1.0.0
 * @since 2020/2/23 17:15
 */
public class TaskManager {

    @Injection
    private TaskInfoMapper taskInfoMapper;
    @Injection
    private TaskRecordMapper taskRecordMapper;
    @Injection
    private WorkDayManager workDayManager;

    /**
     * 执行
     */
    public void execute() {
        // 1.查询任务集合
        List<TaskInfo> list = taskInfoMapper.listValid();
        // 2.遍历执行
        for (TaskInfo task : list) {
            if (this.isExecuteTime(task)) {
                this.doTask(task);
            }
        }
    }

    /**
     * 查询执行记录
     * @param date 日期
     * @return list
     */
    public List<TaskRecord> records(String date) {
        Map<String, String> map = taskInfoMapper.list().stream()
                .collect(Collectors.toMap(TaskInfo::getCode, TaskInfo::getName, (o, n) -> n));
        List<TaskRecord> records = taskRecordMapper.list(date);
        for (TaskRecord record : records) record.setCode(map.get(record.getCode()));
        return records;
    }

    /**
     * 判断任务是否在需要执行的时间
     * @param task 任务
     * @return boolean
     */
    private boolean isExecuteTime(TaskInfo task) {
        Date now = new Date();
        String today = workDayManager.getToday();
        Date start = CommonUtil.parseDate(today + " " + task.getStartTime(), Constant.FORMAT_DATETIME);
        Date end = CommonUtil.parseDate(today + " " + task.getEndTime(), Constant.FORMAT_DATETIME);
        String type = task.getType();
        return (TaskType.IN_WORKDAY.getCode().equals(type) && workDayManager.isTodayWorkDay()
                || TaskType.AFTER_WORKDAY.getCode().equals(type) && workDayManager.isYesterdayWorkDay())
                && now.after(start) && now.before(end);
    }

    /**
     * 执行任务
     * @param task 任务
     */
    private void doTask(TaskInfo task) {
        String date = DateFormatUtils.format(new Date(), Constant.FORMAT_DATE);
        // 2.校验当前任务是否执行中
        int cnt = taskRecordMapper.countExecute(task.getCode(), date, task.getStartTime(), task.getEndTime());
        if (cnt > 0) return;
        // 3.执行目标方法
        TaskRecord record = this.initRecord(task);
        taskRecordMapper.merge(record);
        try {
            TaskHolder.invoke(task.getCode()); // 调用目标方法执行
            // 4.执行完毕修改任务状态
            taskRecordMapper.merge(this.recordEnd(record));
        } catch (Exception e) {
            taskRecordMapper.merge(this.recordException(record, e));    // 执行错误记录错误执行信息
        }

    }

    /**
     * 初始化任务执行记录
     * @param task 任务
     * @return TaskRecord
     */
    private TaskRecord initRecord(TaskInfo task) {
        TaskRecord record = new TaskRecord();
        record.setCode(task.getCode());
        record.setDate(workDayManager.getToday());
        record.setStartTime(workDayManager.getNowTime());
        record.setStatus(ExeStatus.START.getCode());
        return record;
    }


    /**
     * 执行结束记录
     * @param record record
     * @return TaskRecord
     */
    private TaskRecord recordEnd(TaskRecord record) {
        record.setStatus(ExeStatus.END.getCode());
        record.setEndTime(workDayManager.getNowTime());
        return record;
    }

    /**
     * 执行异常记录
     * @param record record
     * @param e      e
     * @return TaskRecord
     */
    private TaskRecord recordException(TaskRecord record, Exception e) {
        record.setStatus(ExeStatus.EXCEPTION.getCode());
        record.setEndTime(workDayManager.getNowTime());
        record.setException(e.getClass().getName());
        return record;
    }

}
