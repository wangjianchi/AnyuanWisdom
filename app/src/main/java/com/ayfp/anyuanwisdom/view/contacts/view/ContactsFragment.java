package com.ayfp.anyuanwisdom.view.contacts.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseFragment;
import com.ayfp.anyuanwisdom.view.contacts.adapter.ContactsAdapter;
import com.ayfp.anyuanwisdom.view.contacts.iview.IContactsView;
import com.ayfp.anyuanwisdom.view.contacts.presenter.ContactsPresenter;

import butterknife.BindView;

/**
 * Created by 建池 on 2017/12/3.
 */

public class ContactsFragment extends BaseFragment<ContactsPresenter> implements IContactsView{
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ContactsAdapter mContactsAdapter;
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_contacts;
    }

    @Override
    protected void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mContactsAdapter = new ContactsAdapter(mPresenter.generateData());
        mRecyclerView.setAdapter(mContactsAdapter);
    }

    @Override
    protected ContactsPresenter createPresenter() {
        return new ContactsPresenter(this);
    }
}
