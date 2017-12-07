package com.ayfp.anyuanwisdom.view.contacts.bean;

import com.ayfp.anyuanwisdom.view.contacts.adapter.ContactsAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by luoxw on 2016/8/10.
 */

public class Person implements MultiItemEntity {


    /**
     * user_id : 2
     * real_name : 2xx
     * job_position : 书记
     * portrait : http://www.etiyi.com.cn/fp/data/files/portrait/2.jpg?v=1512552913
     */

    private String user_id;
    private String real_name;
    private String job_position;
    private String portrait;


    @Override
    public int getItemType() {
        return ContactsAdapter.TYPE_PERSON;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getJob_position() {
        return job_position;
    }

    public void setJob_position(String job_position) {
        this.job_position = job_position;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}