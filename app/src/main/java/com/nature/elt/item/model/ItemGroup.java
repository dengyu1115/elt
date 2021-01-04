package com.nature.elt.item.model;

import com.nature.elt.item.model.Item;

/**
 * 项目分组
 * @author nature
 * @version 1.0.0
 * @since 2020/4/4 18:22
 */
public class ItemGroup extends Item {

    /**
     * 分组
     */
    private String group;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
