package com.ayfp.anyuanwisdom.config.cache;

import com.ayfp.anyuanwisdom.bean.EventCategory;
import com.ayfp.anyuanwisdom.bean.EventDegree;

import java.util.List;

/**
 * Created by 建池 on 2017/12/3.
 */

public class AppCache {
    private static AppCache instance;
    public static synchronized AppCache getInstance(){
        if (instance == null){
            return new AppCache();
        }
        return  instance;
    }
    private List<EventCategory> categoryList;
    private List<EventDegree> degreeList;

    public List<EventCategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<EventCategory> categoryList) {
        this.categoryList = categoryList;
    }

    public List<EventDegree> getDegreeList() {
        return degreeList;
    }

    public void setDegreeList(List<EventDegree> degreeList) {
        this.degreeList = degreeList;
    }
}
