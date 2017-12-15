package com.ayfp.anyuanwisdom.view.contacts.bean;

import com.ayfp.anyuanwisdom.view.contacts.adapter.ContactsAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by luoxw on 2016/8/10.
 */

public class Person implements MultiItemEntity ,Serializable{


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
    /**
     * account : accid2
     * on_line : 1
     */

    private String account;
    private int on_line;
    /**
     * organization : 安远县商务局
     * department : 党支部
     * phone_tel : 13990181917
     */

    private String organization;
    private String department;
    private String phone_tel;


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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getOn_line() {
        return on_line;
    }

    public void setOn_line(int on_line) {
        this.on_line = on_line;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone_tel() {
        return phone_tel;
    }

    public void setPhone_tel(String phone_tel) {
        this.phone_tel = phone_tel;
    }
}