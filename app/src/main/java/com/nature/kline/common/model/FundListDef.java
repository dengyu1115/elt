package com.nature.kline.common.model;

/**
 * 基金涨幅
 * @author nature
 * @version 1.0.0
 * @since 2020/9/17 22:19
 */
public class FundListDef extends BaseModel {
    /**
     * 标题
     */
    private String title;
    /**
     * 基金编码
     */
    private String code;
    /**
     * 类型
     */
    private String type;
    /**
     * 数量
     */
    private Integer count;
    /**
     * 开始日
     */
    private String dateStart;
    /**
     * 结束日
     */
    private String dateEnd;
    /**
     * 起始净值
     */
    private Double netStart;

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}
