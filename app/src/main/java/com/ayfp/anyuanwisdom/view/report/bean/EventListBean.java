package com.ayfp.anyuanwisdom.view.report.bean;

/**
 * @author:: wangjianchi
 * @time: 2018/1/31  14:38.
 * @description:
 */

public class EventListBean {

    /**
     * id : 23
     * title : ceshi
     * report_time : 2018-01-31 15:06:32
     * event_status_id : 1
     * event_status : 上报中
     */

    private String id;
    private String title;
    private String report_time;
    private String event_status_id;
    private String event_status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReport_time() {
        return report_time;
    }

    public void setReport_time(String report_time) {
        this.report_time = report_time;
    }

    public String getEvent_status_id() {
        return event_status_id;
    }

    public void setEvent_status_id(String event_status_id) {
        this.event_status_id = event_status_id;
    }

    public String getEvent_status() {
        return event_status;
    }

    public void setEvent_status(String event_status) {
        this.event_status = event_status;
    }
}
