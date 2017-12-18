package com.ayfp.anyuanwisdom.view.contacts.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseFragment;
import com.ayfp.anyuanwisdom.view.contacts.adapter.ContactsAdapter;
import com.ayfp.anyuanwisdom.view.contacts.iview.IContactsView;
import com.ayfp.anyuanwisdom.view.contacts.presenter.ContactsPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 建池 on 2017/12/3.
 */

public class ContactsFragment extends BaseFragment<ContactsPresenter> implements IContactsView{
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ContactsAdapter mContactsAdapter;
    @Override
    public void loadComplete() {
        dismissProgress();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_contacts;
    }

    @Override
    protected void initViews() {
        showProgress();
        mPresenter.getData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mContactsAdapter = new ContactsAdapter(mPresenter.getList(),getActivity());
        mRecyclerView.setAdapter(mContactsAdapter);
    }

    @Override
    protected ContactsPresenter createPresenter() {
        return new ContactsPresenter(this);
    }

    @Override
    public void getContacts() {
        mContactsAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.layout_search) void search(){
        Intent intent = new Intent(getActivity(), SearchContactsActivity.class);
        startActivity(intent);
    }
}
