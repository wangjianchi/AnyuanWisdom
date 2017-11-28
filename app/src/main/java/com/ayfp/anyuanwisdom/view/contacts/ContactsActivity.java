package com.ayfp.anyuanwisdom.view.contacts;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.view.contacts.adapter.ContactsAdapter;
import com.ayfp.anyuanwisdom.view.contacts.iview.IContactsView;
import com.ayfp.anyuanwisdom.view.contacts.presenter.ContactsPresenter;

import butterknife.BindView;

/**
 * @author:: wangjianchi
 * @time: 2017/11/23  16:40.
 * @description:
 */

public class ContactsActivity extends BaseActivity<ContactsPresenter> implements IContactsView {
    @BindView(R.id.rv_contacts)
    RecyclerView mRecyclerView;
    private ContactsAdapter mContactsAdapter;
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_contacts;
    }

    @Override
    protected void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mContactsAdapter = new ContactsAdapter(mPresenter.generateData());
        mRecyclerView.setAdapter(mContactsAdapter);
    }

    @Override
    protected ContactsPresenter createPresenter() {
        return new ContactsPresenter(this);
    }
}