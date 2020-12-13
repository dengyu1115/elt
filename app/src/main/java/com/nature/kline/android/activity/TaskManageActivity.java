package com.nature.kline.android.activity;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.nature.kline.android.util.PopUtil;
import com.nature.kline.android.util.TextUtil;
import com.nature.kline.android.view.ExcelView;
import com.nature.kline.android.view.SearchBar;
import com.nature.kline.android.view.Selector;
import com.nature.kline.common.enums.TaskStatus;
import com.nature.kline.common.enums.TaskType;
import com.nature.kline.common.manager.TaskInfoManager;
import com.nature.kline.common.model.TaskInfo;
import com.nature.kline.common.util.CommonUtil;
import com.nature.kline.common.util.InstanceHolder;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 任务管理
 * @author nature
 * @version 1.0.0
 * @since 2020/12/6 12:43
 */
public class TaskManageActivity extends BaseListActivity<TaskInfo> {

    private Selector<TaskInfo> taskSel;

    private Selector<String> typeSel, statusSel;

    private Button add, startTime, endTime;

    private final TaskInfoManager taskInfoManager = InstanceHolder.get(TaskInfoManager.class);

    private final List<ExcelView.D<TaskInfo>> ds = Arrays.asList(
            new ExcelView.D<>("code", d -> TextUtil.text(d.getCode()), C, S, CommonUtil.nullsLast(TaskInfo::getCode)),
            new ExcelView.D<>("名称", d -> TextUtil.text(d.getName()), C, C, CommonUtil.nullsLast(TaskInfo::getName)),
            new ExcelView.D<>("类型", d -> TextUtil.text(d.getType()), C, C, CommonUtil.nullsLast(TaskInfo::getType)),
            new ExcelView.D<>("执行开始", d -> TextUtil.text(d.getStartTime()), C, C, CommonUtil.nullsLast(TaskInfo::getStartTime)),
            new ExcelView.D<>("执行结束", d -> TextUtil.text(d.getEndTime()), C, C, CommonUtil.nullsLast(TaskInfo::getEndTime)),
            new ExcelView.D<>("状态", d -> TextUtil.text(d.getStatus()), C, C, CommonUtil.nullsLast(TaskInfo::getStatus))
    );

    @Override
    protected List<ExcelView.D<TaskInfo>> define() {
        return ds;
    }

    @Override
    protected List<TaskInfo> listData() {
        return taskInfoManager.list();
    }


    @Override
    protected void initHeaderViews(SearchBar searchBar) {
        searchBar.addConditionView(add = template.button("+", 30, 30));
    }

    @Override
    protected void initHeaderBehaviours() {
        add.setOnClickListener(v -> PopUtil.confirm(context, "添加任务", this.popAddWindow(), () -> {
            TaskInfo taskInfo = taskSel.getValue();
            if (taskInfo == null) {
                throw new RuntimeException("任务不可为空");
            }
            String type = typeSel.getValue();
            if (StringUtils.isBlank(type)) {
                throw new RuntimeException("请选择类型");
            }
            String status = statusSel.getValue();
            if (StringUtils.isBlank(status)) {
                throw new RuntimeException("请选择状态");
            }
            String timeStart = startTime.getText().toString();
            String timeEnd = endTime.getText().toString();
            if (timeStart.compareTo(timeEnd) >= 0) {
                throw new RuntimeException("结束时间必须大于开始时间");
            }
            TaskInfo ti = new TaskInfo();
            ti.setCode(taskInfo.getCode());
            ti.setName(taskInfo.getName());
            ti.setType(type);
            ti.setStartTime(timeStart);
            ti.setEndTime(timeEnd);
            ti.setStatus(status);
            taskInfoManager.merge(ti);
            PopUtil.alert(context, "保存成功");
        }));
    }

    private View popAddWindow() {
        LinearLayout layout = template.block(800, 1200);
        //layout.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        layout.addView(this.line("任务", taskSel = template.selector(150, 30)));
        layout.addView(this.line("类型", typeSel = template.selector(150, 30)));
        layout.addView(this.line("时间", startTime = template.button("00:00:00", 75, 30),
                endTime = template.button("00:00:00", 75, 30)));
        layout.addView(this.line("状态", statusSel = template.selector(150, 30)));
        List<TaskInfo> items = taskInfoManager.listAll();
        taskSel.mapper(TaskInfo::getName).init().refreshData(items);
        typeSel.mapper(TaskType::getName).init().refreshData(TaskType.codes());
        statusSel.mapper(TaskStatus::getName).init().refreshData(TaskStatus.codes());
        startTime.setOnClickListener(v -> template.timePiker(startTime));
        endTime.setOnClickListener(v -> template.timePiker(endTime));
        return layout;
    }

    @SuppressLint("RtlHardcoded")
    private LinearLayout line(String title, View... views) {
        LinearLayout line = template.line(300, 30);
        line.addView(template.textView(title, 80, 30));
        for (View view : views) {
            line.addView(view);
        }
        line.setGravity(Gravity.LEFT);
        return line;
    }

}
