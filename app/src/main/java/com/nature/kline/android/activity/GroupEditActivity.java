package com.nature.kline.android.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.nature.kline.android.util.PopUtil;
import com.nature.kline.android.util.ViewTemplate;
import com.nature.kline.android.util.ViewUtil;
import com.nature.kline.android.view.Selector;
import com.nature.kline.common.constant.DefaultGroup;
import com.nature.kline.common.manager.GroupManager;
import com.nature.kline.common.model.Group;
import com.nature.kline.common.util.InstanceHolder;

/**
 * 分组编辑
 * @author nature
 * @version 1.0.0
 * @since 2020/6/13 17:28
 */
public class GroupEditActivity extends AppCompatActivity {

    private Context context;

    private LinearLayout page;
    private EditText code, name, remark;
    private Button save, delete, load;
    private Selector<String> type;

    private final GroupManager groupManager = InstanceHolder.get(GroupManager.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = GroupEditActivity.this;
        this.makeStructure();
        this.initBehaviours();
        this.setContentView(page);
        ViewUtil.initActivity(GroupEditActivity.this);
    }

    private void initBehaviours() {
        type.mapper(DefaultGroup::getName).init().refreshData(DefaultGroup.codes());
        save.setOnClickListener(v -> {
            String code = this.code.getText().toString();
            if (code.isEmpty()) {
                PopUtil.alert(context, "请填写编号");
                return;
            }
            String name = this.name.getText().toString();
            if (name.isEmpty()) {
                PopUtil.alert(context, "请填写名称");
                return;
            }
            String remark = this.remark.getText().toString();
            PopUtil.confirm(context, "操作确认", "确定保存吗？", () -> {
                Group group = new Group();
                group.setCode(code);
                group.setName(name);
                group.setRemark(remark);
                groupManager.merge(group);
                PopUtil.alert(context, "保存成功");
            });
        });

        delete.setOnClickListener(v -> {
            String code = this.code.getText().toString();
            if (code.isEmpty()) {
                PopUtil.alert(context, "请填写编号");
                return;
            }
            PopUtil.confirm(context, "操作确认", "确定删除吗？", () -> {
                groupManager.delete(code, type.getValue());
                PopUtil.alert(context, "删除成功");
            });
        });

        this.load.setOnClickListener(v -> {
            String code = this.code.getText().toString();
            if (code.isEmpty()) {
                PopUtil.alert(context, "请填写编号");
                return;
            }
            PopUtil.confirm(context, "操作确认", "确定加载为已存储的内容吗？", () -> {
                Group group = groupManager.findByCode(code, type.getValue());
                if (group == null) {
                    PopUtil.alert(context, "无相应分组存在");
                    return;
                }
                this.name.setText(group.getName());
                this.remark.setText(group.getRemark());
            });
        });
    }

    public void makeStructure() {
        ViewTemplate template = ViewTemplate.build(context);
        page = template.linearPage();
        page.setGravity(Gravity.CENTER);
        LinearLayout tl = template.line(300, 30);
        LinearLayout cl = template.line(300, 30);
        LinearLayout nl = template.line(300, 30);
        LinearLayout rl = template.line(300, 90);
        LinearLayout el = template.line(300, 30);
        tl.addView(template.textView("类型：", 100, 30));
        tl.addView(type = template.selector(200, 30));
        cl.addView(template.textView("分组编号：", 100, 30));
        cl.addView(code = template.editText(200, 30));
        nl.addView(template.textView("分组名称：", 100, 30));
        nl.addView(name = template.editText(200, 30));
        rl.addView(template.textView("备注：", 100, 30));
        rl.addView(remark = template.areaText(200, 90));
        el.addView(save = template.button("保存", 60, 30));
        el.addView(delete = template.button("删除", 60, 30));
        el.addView(load = template.button("加载", 60, 30));
        page.addView(tl);
        page.addView(cl);
        page.addView(nl);
        page.addView(rl);
        page.addView(el);
    }

}
