package com.nature.elt.common.model;

import java.util.List;

/**
 * 折线
 * @author nature
 * @version 1.0.0
 * @since 2020/8/30 21:49
 */
public class LineDef extends BaseModel {
    /**
     * 标题
     */
    private String title;
    /**
     * 颜色
     */
    private int color;
    /**
     * sql
     */
    private String sql;
    /**
     * 折线数据
     */
    private List<Line> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Line> getList() {
        return list;
    }

    public void setList(List<Line> list) {
        this.list = list;
    }
}
