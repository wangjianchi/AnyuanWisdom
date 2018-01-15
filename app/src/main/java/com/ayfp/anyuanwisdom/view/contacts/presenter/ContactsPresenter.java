package com.ayfp.anyuanwisdom.view.contacts.presenter;

import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.retrofit.AppResultData;
import com.ayfp.anyuanwisdom.retrofit.BaseObserver;
import com.ayfp.anyuanwisdom.retrofit.RetrofitService;
import com.ayfp.anyuanwisdom.view.contacts.bean.ContactsList;
import com.ayfp.anyuanwisdom.view.contacts.bean.Level0Item;
import com.ayfp.anyuanwisdom.view.contacts.bean.Level1Item;
import com.ayfp.anyuanwisdom.view.contacts.bean.Person;
import com.ayfp.anyuanwisdom.view.contacts.iview.IContactsView;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        getAddressBook();
    }
    @Override
    public void networkConnected() {

    }
    private void getAddressBook(){
        RetrofitService.getApi().getAddressBook(RetrofitService.TOKEN, Preferences.getUserName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<AppResultData<List<ContactsList>>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<List<ContactsList>>>() {

                    @Override
                    public void loadSuccess(AppResultData<List<ContactsList>> listAppResultData) {
                        mView.loadComplete();
                        if (listAppResultData.getStatus() == RetrofitService.SUCCESS && listAppResultData.getResult().size() > 0){
                            for (ContactsList contactsList: listAppResultData.getResult()){
                                Level0Item lv0 = new Level0Item(contactsList.getName(), contactsList.getLine_count());
                                for (ContactsList.ListBeanX listBeanX : contactsList.getList()){
                                    Level1Item lv1 = new Level1Item(listBeanX.getName(),listBeanX.getLine_count());
                                    for (Person person : listBeanX.getList()){
                                        lv1.addSubItem(person);
                                    }
                                    lv0.addSubItem(lv1);
                                }
                              list.add(lv0);
                            }
                            mView.getContacts();
                        }
                    }
                });
    }

    public ArrayList<MultiItemEntity> getList() {
        return list;
    }
}
