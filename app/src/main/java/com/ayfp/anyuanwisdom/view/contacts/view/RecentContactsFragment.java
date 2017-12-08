package com.ayfp.anyuanwisdom.view.contacts.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseFragment;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.base.MyApplication;
import com.ayfp.anyuanwisdom.view.contacts.adapter.RecentContactsAdapter;
import com.ayfp.anyuanwisdom.weidgts.SpringView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 建池 on 2017/12/3.
 */

public class RecentContactsFragment extends BaseFragment {
    @BindView(R.id.springView)
    SpringView mSpringView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private List<RecentContact> mData = new ArrayList<>();
    private RecentContactsAdapter mAdapter;
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_recent_contacts;
    }

    @Override
    protected void initViews() {
        getRecentContacts();
        mSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                getRecentContacts();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecentContactsAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ChatActivity.start(MyApplication.getContext(),mData.get(position).getContactId(),mData.get(position).getFromNick());
            }
        });
    }

    private void getRecentContacts(){
        showProgress();
        NIMClient.getService(MsgService.class).queryRecentContacts().setCallback(new RequestCallbackWrapper<List<RecentContact>>() {
            @Override
            public void onResult(int i, List<RecentContact> recentContacts, Throwable throwable) {
                if (recentContacts != null){
                    Log.i("RecentContactsFragment", "onResult: "+ JSON.toJSONString(recentContacts));
                    mData.clear();
                    mData.addAll(recentContacts);
                    mAdapter.notifyDataSetChanged();
                }
                dismissProgress();
                mSpringView.onFinishFreshAndLoad();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getRecentContacts();
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
}
