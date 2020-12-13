package com.nature.kline.common.manager;

import com.nature.kline.common.ioc.annotation.Injection;
import com.nature.kline.common.mapper.TaskInfoMapper;
import com.nature.kline.common.model.TaskInfo;
import com.nature.kline.common.util.TaskHolder;

import java.util.List;

/**
 * 任务信息
 * @author nature
 * @version 1.0.0
 * @since 2020/12/6 14:42
 */
public class TaskInfoManager {

    @Injection
    private TaskInfoMapper taskInfoMapper;

    public List<TaskInfo> listAll() {
        return TaskHolder.listAll();
    }

    public List<TaskInfo> list() {
        return taskInfoMapper.list();
    }

    public int merge(TaskInfo d){
        return taskInfoMapper.merge(d);
    }
}
