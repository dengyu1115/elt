package com.nature.kline.android.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.fastjson.JSON;
import com.nature.kline.android.util.PopUtil;
import com.nature.kline.android.util.TextUtil;
import com.nature.kline.android.util.ViewUtil;
import com.nature.kline.android.view.ExcelView;
import com.nature.kline.android.view.Selector;
import com.nature.kline.common.constant.DefType;
import com.nature.kline.common.manager.DefinitionManager;
import com.nature.kline.common.manager.FundListDefManager;
import com.nature.kline.common.model.Definition;
import com.nature.kline.common.model.FundListDef;
import com.nature.kline.common.util.InstanceHolder;
import com.nature.kline.common.util.Sorter;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FundListDefActivity extends AppCompatActivity {

    public static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;

    private Context context;

    private LinearLayout page;

    private int width, height;

    private static final int C = 0, S = 1, E = 2;

    private final DefinitionManager definitionManager = InstanceHolder.get(DefinitionManager.class);
    private final FundListDefManager fundListDefManager = InstanceHolder.get(FundListDefManager.class);

    private ExcelView<FundListDef> excel;

    private Selector<Definition> selector;

    private Selector<String> type;

    private EditText code, title, count, dateStart, dateEnd;

    private Button add, delete, addDef;

    private LinearLayout header, body, footer;

    private final List<ExcelView.D<FundListDef>> ds = Arrays.asList(
            new ExcelView.D<>("名称", d -> TextUtil.text(d.getTitle()), C, S, Sorter.nullsLast(FundListDef::getTitle)),
            new ExcelView.D<>("CODE", d -> TextUtil.text(d.getCode()), C, C, Sorter.nullsLast(FundListDef::getCode)),
            new ExcelView.D<>("类型", d -> TextUtil.text(fundListDefManager.getTypeName(d.getType())), C, E, Sorter.nullsLast(FundListDef::getType)),
            new ExcelView.D<>("数量", d -> TextUtil.text(d.getCount()), C, E, Sorter.nullsLast(FundListDef::getCount)),
            new ExcelView.D<>("开始日期", d -> TextUtil.text(d.getDateStart()), C, E, Sorter.nullsLast(FundListDef::getDateStart)),
            new ExcelView.D<>("结束日期", d -> TextUtil.text(d.getDateEnd()), C, E, Sorter.nullsLast(FundListDef::getDateEnd)),
            new ExcelView.D<>("操作", d -> "-", C, C, this::popDelDef)
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = FundListDefActivity.this;
        this.makeStructure();
        this.setContentView(page);
        ViewUtil.initActivity(this);
        this.initFunctions();
    }

    private void initFunctions() {
        selector.mapper(Definition::getTitle).onChangeRun(this::refreshExcel).init();
        this.refreshSelector();
        add.setOnClickListener(v -> popEditRule());
        delete.setOnClickListener(v -> popDelRule());
        addDef.setOnClickListener(v -> popEditDef());
    }

    private void popDelRule() {
        PopUtil.confirm(context, "删除显示规则", "确定要删除吗？", () -> {
            Definition definition = selector.getValue();
            if (definition == null) {
                PopUtil.alert(context, "请选择要删除的规则");
                return;
            }
            definitionManager.delete(DefType.FUND_LIST, definition.getCode());
            PopUtil.alert(context, "删除成功");
            this.refreshSelector();
        });
    }

    private void popEditRule() {
        PopUtil.confirm(context, "添加显示规则", popWindow(), () -> {
            String code = this.code.getText().toString();
            if (StringUtils.isBlank(code)) {
                PopUtil.alert(context, "编号不能为空");
                return;
            }
            String title = this.title.getText().toString();
            if (StringUtils.isBlank(title)) {
                PopUtil.alert(context, "标题不能为空");
                return;
            }
            Definition definition = new Definition();
            definition.setType(DefType.FUND_LIST);
            definition.setCode(code);
            definition.setTitle(title);
            definitionManager.merge(definition);
            PopUtil.alert(context, "保存成功");
            this.refreshSelector();
        });
    }

    @SuppressLint("SimpleDateFormat")
    private void popEditDef() {
        PopUtil.confirm(context, "添加显示规则", editShow(), () -> {
            Definition definition = selector.getValue();
            if (definition == null) {
                PopUtil.alert(context, "请选择规则");
                return;
            }
            String code = this.code.getText().toString();
            if (StringUtils.isBlank(code)) {
                PopUtil.alert(context, "编号不能为空");
                return;
            }
            String title = this.title.getText().toString();
            if (StringUtils.isBlank(title)) {
                PopUtil.alert(context, "标题不能为空");
                return;
            }
            String type = this.type.getValue();
            if (StringUtils.isBlank(type)) {
                PopUtil.alert(context, "请选择类型");
                return;
            }
            String dateStart = null;
            String dateEnd = null;
            Integer count = null;
            if (FundListDefManager.DEFINED.equals(type)) {
                dateStart = this.dateStart.getText().toString();
                if (StringUtils.isBlank(dateStart)) {
                    PopUtil.alert(context, "请填写开始日期");
                    return;
                } else {
                    try {
                        new SimpleDateFormat("yyyy-MM-dd").parse(dateStart);
                    } catch (ParseException e) {
                        PopUtil.alert(context, "请填写正确的日期格式");
                        return;
                    }
                }
                dateEnd = this.dateEnd.getText().toString();
                if (StringUtils.isNotBlank(dateEnd)) {
                    try {
                        new SimpleDateFormat("yyyy-MM-dd").parse(dateEnd);
                    } catch (ParseException e) {
                        PopUtil.alert(context, "请填写正确的日期格式");
                        return;
                    }
                }
            } else {
                String s = this.count.getText().toString();
                if (StringUtils.isBlank(s)) {
                    PopUtil.alert(context, "请填写数量");
                    return;
                }
                if (!s.matches("^[0-9]*$")) {
                    PopUtil.alert(context, "数量必须是数字");
                    return;
                }
                count = Integer.valueOf(s);
            }
            String json = definition.getJson();
            List<FundListDef> list;
            if (StringUtils.isBlank(json)) {
                list = new ArrayList<>();
            } else {
                list = JSON.parseArray(json, FundListDef.class);
            }
            FundListDef def = new FundListDef();
            def.setCode(code);
            def.setTitle(title);
            def.setType(type);
            def.setCount(count);
            def.setDateStart(dateStart);
            def.setDateEnd(dateEnd);
            list.add(def);
            list.sort(Comparator.comparing(FundListDef::getCode));
            definition.setJson(JSON.toJSONString(list));
            definitionManager.merge(definition);
            PopUtil.alert(context, "保存成功");
            this.refreshExcel();
        });
    }

    private void popDelDef(FundListDef o) {
        PopUtil.confirm(context, "删除显示规则", "确定删除 " + o.getTitle() + " 吗？", () -> {
            Definition definition = selector.getValue();
            if (definition == null) {
                PopUtil.alert(context, "请选择规则");
                return;
            }
            List<FundListDef> list = JSON.parseArray(definition.getJson(), FundListDef.class);
            for (FundListDef def : list) {
                if (def.getCode().equals(o.getCode())) {
                    list.remove(def);
                    break;
                }
            }
            definition.setJson(JSON.toJSONString(list));
            definitionManager.merge(definition);
            PopUtil.alert(context, "删除成功");
            this.refreshExcel();
        });
    }

    private void refreshSelector() {
        selector.refreshData(definitionManager.list(DefType.FUND_LIST));
    }

    private void refreshExcel() {
        Definition d = selector.getValue();
        if (d == null) return;
        String json = d.getJson();
        if (StringUtils.isBlank(json)) excel.data(new ArrayList<>());
        else excel.data(JSON.parseArray(json, FundListDef.class));
    }

    private void refreshType() {
        type.refreshData(fundListDefManager.listAllType());
    }

    private void makeStructure() {
        this.initMainStructure();
        excel = new ExcelView<>(context, 5);
        excel.define(ds);
        selector = new Selector<>(context);
        add = ViewUtil.button(context, "添加");
        delete = ViewUtil.button(context, "删除");
        addDef = ViewUtil.button(context, "添加");
        header.addView(add);
        header.addView(selector);
        header.addView(delete);
        body.addView(excel);
        footer.addView(addDef);
    }

    /**
     * 初始化主要页面架构
     */
    private void initMainStructure() {
        page = new LinearLayout(context);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        header = new LinearLayout(context);
        body = new LinearLayout(context);
        footer = new LinearLayout(context);
        header.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, (int) (height * 0.05)));
        header.setGravity(Gravity.CENTER);
        body.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, (int) (height * 0.90)));
        footer.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, (int) (height * 0.05)));
        footer.setGravity(Gravity.CENTER);
        page.addView(header);
        page.addView(body);
        page.addView(footer);
    }

    private LinearLayout popWindow() {
        LinearLayout line = new LinearLayout(context);
        line.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (width * 0.5d), (int) (height * 0.5d));
        line.setLayoutParams(params);
        LinearLayout cl = this.line();
        LinearLayout nl = this.line();
        cl.addView(ViewUtil.textView(context, "编号："));
        cl.addView(code = ViewUtil.editText(context));
        nl.addView(ViewUtil.textView(context, "标题："));
        nl.addView(title = ViewUtil.editText(context));
        line.addView(cl);
        line.addView(nl);
        return line;
    }

    private LinearLayout editShow() {
        LinearLayout line = new LinearLayout(context);
        line.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (width * 0.5d), (int) (height * 0.5d));
        line.setLayoutParams(params);
        LinearLayout n1 = this.line();
        LinearLayout n2 = this.line();
        LinearLayout n3 = this.line();
        LinearLayout n4 = this.line();
        LinearLayout n5 = this.line();
        LinearLayout n6 = this.line();
        n1.addView(ViewUtil.textView(context, "编号："));
        n1.addView(code = ViewUtil.editText(context));
        n2.addView(ViewUtil.textView(context, "标题："));
        n2.addView(title = ViewUtil.editText(context));
        n3.addView(ViewUtil.textView(context, "类型："));
        n3.addView(type = new Selector<>(context));
        n4.addView(ViewUtil.textView(context, "数量："));
        n4.addView(count = ViewUtil.editText(context));
        n5.addView(ViewUtil.textView(context, "开始日期："));
        n5.addView(dateStart = ViewUtil.editText(context));
        n6.addView(ViewUtil.textView(context, "结束日期："));
        n6.addView(dateEnd = ViewUtil.editText(context));
        type.mapper(fundListDefManager::getTypeName);
        type.init();
        this.refreshType();
        line.addView(n1);
        line.addView(n2);
        line.addView(n3);
        line.addView(n4);
        line.addView(n5);
        line.addView(n6);
        return line;
    }

    private LinearLayout line() {
        LinearLayout line = new LinearLayout(context);
        line.setGravity(Gravity.CENTER);
        line.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(MATCH_PARENT, (int) (height * 0.05d));
        line.setLayoutParams(param);
        return line;
    }

}
