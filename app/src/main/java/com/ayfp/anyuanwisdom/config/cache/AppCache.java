package com.ayfp.anyuanwisdom.config.cache;

import com.ayfp.anyuanwisdom.bean.EventCategory;
import com.ayfp.anyuanwisdom.bean.EventConfig;
import com.ayfp.anyuanwisdom.bean.EventDegree;
import com.ayfp.anyuanwisdom.bean.Town;
import com.ayfp.anyuanwisdom.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 建池 on 2017/12/3.
 */

public class AppCache {
    private static AppCache instance;
    public static synchronized AppCache getInstance(){
        if (instance == null){
            instance = new AppCache();
        }
        return  instance;
    }
    private List<EventCategory> categoryList = new ArrayList<>();
    private List<EventDegree> degreeList = new ArrayList<>();
    private List<EventConfig.EventStatusBean> mStatusList = new ArrayList<>();
    private List<Town> mTowns = new ArrayList<>();
    private UserBean mUserBean;

    public UserBean getUserBean() {
        return mUserBean;
    }

    public void setUserBean(UserBean userBean) {
        mUserBean = userBean;
    }

    public List<EventCategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<EventCategory> list) {
        this.categoryList = list;
    }

    public List<EventDegree> getDegreeList() {
        return degreeList;
    }

    public void setDegreeList(List<EventDegree> degreeList) {
        this.degreeList = degreeList;
    }

    public List<Town> getTowns() {
        return mTowns;
    }

    public void setTowns(List<Town> towns) {
        mTowns = towns;
    }

    public List<EventConfig.EventStatusBean> getStatusList() {
        return mStatusList;
    }

    public void setStatusList(List<EventConfig.EventStatusBean> statusList) {
        mStatusList = statusList;
    }
}
