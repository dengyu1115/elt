package com.nature.elt.common.manager;

import com.nature.elt.android.db.BaseDB;
import com.nature.elt.common.http.ItemHttp;
import com.nature.elt.common.ioc.annotation.Injection;
import com.nature.elt.common.mapper.ItemMapper;
import com.nature.elt.common.model.Item;

import java.util.Comparator;
import java.util.List;

/**
 * item
 * @author nature
 * @version 1.0.0
 * @since 2020/4/19 11:35
 */
public class ItemManager {

    private static final int BATCH_SIZE = 200;

    @Injection
    private ItemMapper itemMapper;
    @Injection
    private ItemHttp itemHttp;

    public int reloadAll() {
        itemMapper.delete();
        return this.batchMerge(itemHttp.list());
    }


    /**
     * 批量保存
     * @param list list
     * @return int
     */
    public int batchMerge(List<Item> list) {
        if (list == null || list.isEmpty()) return 0;
        return BaseDB.create().batchExec(list, BATCH_SIZE, itemMapper::batchMerge);
    }

    /**
     * 查询
     * @return list
     */
    public List<Item> list() {
        return this.listByType(null);
    }

    /**
     * 查询
     * @param type 类型
     * @return list
     */
    public List<Item> listByType(String type) {
        return itemMapper.listByType(type);
    }

    /**
     * 按关键字查询
     * @param keyWord keyWord
     * @return list
     */
    public List<Item> listByKeyWord(String keyWord) {
        List<Item> items = itemMapper.listByKeyWord(keyWord);
        items.sort(Comparator.comparing(Item::getCode));
        return items;
    }
}
