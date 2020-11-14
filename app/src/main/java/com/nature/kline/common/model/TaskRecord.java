package com.nature.kline.common.model;

/**
 * 任务记录
 * @author nature
 * @version 1.0.0
 * @since 2020/2/23 21:36
 */
public class TaskRecord extends BaseModel {

    private String code;

    private String date;

    private String startTime;

    private String endTime;

    private String status;

    private String exception;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
