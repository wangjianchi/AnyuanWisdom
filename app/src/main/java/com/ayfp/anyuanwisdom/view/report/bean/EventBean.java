package com.ayfp.anyuanwisdom.view.report.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:: wangjianchi
 * @time: 2018/1/31  16:07.
 * @description:
 */

public class EventBean implements Parcelable {

    /**
     * id : 26
     * user_name : 13990181916
     * title : dffddfa
     * event_category_id : 1
     * event_degree_id : 2
     * content : dffddfafor
     * report_time : 2018-01-31 16:24:48
     * town_id : 3
     * village_id : 21
     * house_number :
     * event_status_id : 2
     * event_category : 教育
     * event_degree : 较重
     * event_status : 处理中
     * imgs : []
     */

    private String id;
    private String user_name;
    private String title;
    private String event_category_id;
    private String event_degree_id;
    private String content;
    private String report_time;
    private String town_id;
    private String village_id;
    private String house_number;
    private String event_status_id;
    private String event_category;
    private String event_degree;
    private String event_status;
    /**
     * town_name : 车头镇
     * village_name : 龙头村委会
     * imgs :
     */

    private String town_name;
    private String village_name;
    private String imgs;
    private List<ReportImageBean> imageList;

    public List<ReportImageBean> getImageList() {
        return imageList;
    }

    public void setImageList(List<ReportImageBean> imageList) {
        this.imageList = imageList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEvent_category_id() {
        return event_category_id;
    }

    public void setEvent_category_id(String event_category_id) {
        this.event_category_id = event_category_id;
    }

    public String getEvent_degree_id() {
        return event_degree_id;
    }

    public void setEvent_degree_id(String event_degree_id) {
        this.event_degree_id = event_degree_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReport_time() {
        return report_time;
    }

    public void setReport_time(String report_time) {
        this.report_time = report_time;
    }

    public String getTown_id() {
        return town_id;
    }

    public void setTown_id(String town_id) {
        this.town_id = town_id;
    }

    public String getVillage_id() {
        return village_id;
    }

    public void setVillage_id(String village_id) {
        this.village_id = village_id;
    }

    public String getHouse_number() {
        return house_number;
    }

    public void setHouse_number(String house_number) {
        this.house_number = house_number;
    }

    public String getEvent_status_id() {
        return event_status_id;
    }

    public void setEvent_status_id(String event_status_id) {
        this.event_status_id = event_status_id;
    }

    public String getEvent_category() {
        return event_category;
    }

    public void setEvent_category(String event_category) {
        this.event_category = event_category;
    }

    public String getEvent_degree() {
        return event_degree;
    }

    public void setEvent_degree(String event_degree) {
        this.event_degree = event_degree;
    }

    public String getEvent_status() {
        return event_status;
    }

    public void setEvent_status(String event_status) {
        this.event_status = event_status;
    }


    public String getTown_name() {
        return town_name;
    }

    public void setTown_name(String town_name) {
        this.town_name = town_name;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public EventBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.user_name);
        dest.writeString(this.title);
        dest.writeString(this.event_category_id);
        dest.writeString(this.event_degree_id);
        dest.writeString(this.content);
        dest.writeString(this.report_time);
        dest.writeString(this.town_id);
        dest.writeString(this.village_id);
        dest.writeString(this.house_number);
        dest.writeString(this.event_status_id);
        dest.writeString(this.event_category);
        dest.writeString(this.event_degree);
        dest.writeString(this.event_status);
        dest.writeString(this.town_name);
        dest.writeString(this.village_name);
        dest.writeString(this.imgs);
        dest.writeList(this.imageList);
    }

    protected EventBean(Parcel in) {
        this.id = in.readString();
        this.user_name = in.readString();
        this.title = in.readString();
        this.event_category_id = in.readString();
        this.event_degree_id = in.readString();
        this.content = in.readString();
        this.report_time = in.readString();
        this.town_id = in.readString();
        this.village_id = in.readString();
        this.house_number = in.readString();
        this.event_status_id = in.readString();
        this.event_category = in.readString();
        this.event_degree = in.readString();
        this.event_status = in.readString();
        this.town_name = in.readString();
        this.village_name = in.readString();
        this.imgs = in.readString();
        this.imageList = new ArrayList<ReportImageBean>();
        in.readList(this.imageList, ReportImageBean.class.getClassLoader());
    }

    public static final Creator<EventBean> CREATOR = new Creator<EventBean>() {
        @Override
        public EventBean createFromParcel(Parcel source) {
            return new EventBean(source);
        }

        @Override
        public EventBean[] newArray(int size) {
            return new EventBean[size];
        }
    };
}
