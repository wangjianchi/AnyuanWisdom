package com.ayfp.anyuanwisdom.view.contacts.view;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author:: wangjianchi
 * @time: 2017/11/23  16:40.
 * @description:
 */

public class ContactsActivity extends BaseActivity {
    @BindView(R.id.tv_recent_contacts)
    TextView textRecentContacts;
    @BindView(R.id.tv_contacts)
    TextView textContacts;
    private ContactsFragment contactsFragment;
    private RecentContactsFragment mRecentContactsFragment;
    private FragmentTransaction transaction;

    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_contacts;
    }

    @Override
    protected void initViews() {
        recentContacts();
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.tv_recent_contacts) void recentContacts(){
        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        if (contactsFragment != null){
            transaction.hide(contactsFragment);
        }
        if (mRecentContactsFragment == null){
            mRecentContactsFragment = new RecentContactsFragment();
            transaction.add(R.id.layout_container,mRecentContactsFragment);
        }else {
            transaction.show(mRecentContactsFragment);
        }
        transaction.commit();
    }
    @OnClick(R.id.tv_contacts) void contacts(){
        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        if (mRecentContactsFragment != null){
            transaction.hide(mRecentContactsFragment);
        }
        if (contactsFragment == null){
            contactsFragment = new ContactsFragment();
            transaction.add(R.id.layout_container,contactsFragment);
        }else {
            transaction.show(contactsFragment);
        }
        transaction.commit();
    }


    @OnClick(R.id.iv_back) void back(){
        finish();
    }
}
