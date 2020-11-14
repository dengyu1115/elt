package com.nature.kline.common.model;

/**
 * 指标数据
 * @author nature
 * @version 1.0.0
 * @since 2020/8/21 18:22
 */
public class Quota extends BaseModel {

    private String code;

    private String status;

    private String date;

    private Double syl;

    private Double szZ;

    private Double gbZ;

    private Double szLt;

    private Double gbLt;

    private Double price;

    private Double count;

    private Double sylRate;

    private Double szZRate;

    private Double gbZRate;

    private Double szLtRate;

    private Double gbLtRate;

    private Double priceRate;

    private Double countRate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getSyl() {
        return syl;
    }

    public void setSyl(Double syl) {
        this.syl = syl;
    }

    public Double getSzZ() {
        return szZ;
    }

    public void setSzZ(Double szZ) {
        this.szZ = szZ;
    }

    public Double getGbZ() {
        return gbZ;
    }

    public void setGbZ(Double gbZ) {
        this.gbZ = gbZ;
    }

    public Double getSzLt() {
        return szLt;
    }

    public void setSzLt(Double szLt) {
        this.szLt = szLt;
    }

    public Double getGbLt() {
        return gbLt;
    }

    public void setGbLt(Double gbLt) {
        this.gbLt = gbLt;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public Double getSylRate() {
        return sylRate;
    }

    public void setSylRate(Double sylRate) {
        this.sylRate = sylRate;
    }

    public Double getSzZRate() {
        return szZRate;
    }

    public void setSzZRate(Double szZRate) {
        this.szZRate = szZRate;
    }

    public Double getGbZRate() {
        return gbZRate;
    }

    public void setGbZRate(Double gbZRate) {
        this.gbZRate = gbZRate;
    }

    public Double getSzLtRate() {
        return szLtRate;
    }

    public void setSzLtRate(Double szLtRate) {
        this.szLtRate = szLtRate;
    }

    public Double getGbLtRate() {
        return gbLtRate;
    }

    public void setGbLtRate(Double gbLtRate) {
        this.gbLtRate = gbLtRate;
    }

    public Double getPriceRate() {
        return priceRate;
    }

    public void setPriceRate(Double priceRate) {
        this.priceRate = priceRate;
    }

    public Double getCountRate() {
        return countRate;
    }

    public void setCountRate(Double countRate) {
        this.countRate = countRate;
    }
}
