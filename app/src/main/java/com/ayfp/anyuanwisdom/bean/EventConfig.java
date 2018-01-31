package com.ayfp.anyuanwisdom.bean;

import java.util.List;

/**
 * @author:: wangjianchi
 * @time: 2018/1/31  11:28.
 * @description:
 */

public class EventConfig {

    /**
     * event_category : [{"cate_name":"教育","id":"1"},{"cate_name":"医疗","id":"2"},{"cate_name":"疾病","id":"3"},{"cate_name":"基础设施","id":"7"}]
     * event_degree : [{"degree_name":"严重","id":"1"},{"degree_name":"较重","id":"2"},{"degree_name":"一般","id":"3"}]
     * event_status : [{"id":"1","status":"上报中"},{"id":"2","status":"处理中"},{"id":"3","status":"处理完成"}]
     * upload_location_distance : 500
     */

    private int upload_location_distance;
    private List<EventCategory> event_category;
    private List<EventDegree> event_degree;
    private List<EventStatusBean> event_status;

    public int getUpload_location_distance() {
        return upload_location_distance;
    }

    public void setUpload_location_distance(int upload_location_distance) {
        this.upload_location_distance = upload_location_distance;
    }

    public List<EventCategory> getEvent_category() {
        return event_category;
    }

    public void setEvent_category(List<EventCategory> event_category) {
        this.event_category = event_category;
    }

    public List<EventDegree> getEvent_degree() {
        return event_degree;
    }

    public void setEvent_degree(List<EventDegree> event_degree) {
        this.event_degree = event_degree;
    }

    public List<EventStatusBean> getEvent_status() {
        return event_status;
    }

    public void setEvent_status(List<EventStatusBean> event_status) {
        this.event_status = event_status;
    }


    public static class EventStatusBean {
        /**
         * id : 1
         * status : 上报中
         */

        private String id;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
