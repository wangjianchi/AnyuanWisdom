package com.ayfp.anyuanwisdom.view.contacts.view;

import android.support.v7.widget.RecyclerView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseFragment;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.weidgts.SpringView;

import butterknife.BindView;

/**
 * Created by 建池 on 2017/12/3.
 */

public class RecentContactsFragment extends BaseFragment {
    @BindView(R.id.springView)
    SpringView mSpringView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_recent_contacts;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
}
