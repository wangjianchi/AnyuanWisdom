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

    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_contacts;
    }

    @Override
    protected void initViews() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        contactsFragment = new ContactsFragment();
        transaction.add(R.id.layout_container,contactsFragment);
        transaction.commit();
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.tv_recent_contacts) void recentContacts(){

    }
    @OnClick(R.id.tv_contacts) void contacts(){

    }


    @OnClick(R.id.iv_back) void back(){
        finish();
    }
}
