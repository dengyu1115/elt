package com.nature.elt.common.model;

import java.util.Objects;

/**
 * 定义
 * @author nature
 * @version 1.0.0
 * @since 2020/9/19 16:00
 */
public class Definition extends BaseModel {
    /**
     * 标题
     */
    private String title;
    /**
     * 编码
     */
    private String code;
    /**
     * 类型
     */
    private String type;
    /**
     * 描述
     */
    private String desc;
    /**
     * json
     */
    private String json;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Definition that = (Definition) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(code, that.code) &&
                Objects.equals(type, that.type) &&
                Objects.equals(desc, that.desc) &&
                Objects.equals(json, that.json);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, code, type, desc, json);
    }
}
