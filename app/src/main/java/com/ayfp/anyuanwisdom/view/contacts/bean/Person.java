package com.ayfp.anyuanwisdom.view.contacts.bean;

import com.ayfp.anyuanwisdom.view.contacts.adapter.ContactsAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by luoxw on 2016/8/10.
 */

public class Person implements MultiItemEntity {
    public Person(String name, int age) {
        this.age = age;
        this.name = name;
    }

    public String name;
    public int age;

    @Override
    public int getItemType() {
        return ContactsAdapter.TYPE_PERSON;
    }
}