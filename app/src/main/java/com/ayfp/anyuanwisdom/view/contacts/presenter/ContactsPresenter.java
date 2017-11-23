package com.ayfp.anyuanwisdom.view.contacts.presenter;

import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.view.contacts.bean.Level0Item;
import com.ayfp.anyuanwisdom.view.contacts.bean.Level1Item;
import com.ayfp.anyuanwisdom.view.contacts.bean.Person;
import com.ayfp.anyuanwisdom.view.contacts.iview.IContactsView;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author:: wangjianchi
 * @time: 2017/11/23  17:59.
 * @description:
 */

public class ContactsPresenter implements IBasePresenter {
    private IContactsView mView;
    ArrayList<MultiItemEntity> list= new ArrayList<>();
    public ContactsPresenter(IContactsView view){
        this.mView = view;
    }
    @Override
    public void getData() {
        list = generateData();
    }

    @Override
    public void networkConnected() {

    }

    public ArrayList<MultiItemEntity> generateData() {
        int lv0Count = 9;
        int lv1Count = 3;
        int personCount = 5;

        String[] nameList = {"Bob", "Andy", "Lily", "Brown", "Bruce"};
        Random random = new Random();

        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < lv0Count; i++) {
            Level0Item lv0 = new Level0Item("This is " + i + "th item in Level 0", "subtitle of " + i);
            for (int j = 0; j < lv1Count; j++) {
                Level1Item lv1 = new Level1Item("Level 1 item: " + j, "(no animation)");
                for (int k = 0; k < personCount; k++) {
                    lv1.addSubItem(new Person(nameList[k], random.nextInt(40)));
                }
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }
        return res;
    }

    public ArrayList<MultiItemEntity> getList() {
        return list;
    }
}
