package com.ayfp.anyuanwisdom.view.notice;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.view.notice.adapter.NoticeAdapter;
import com.ayfp.anyuanwisdom.view.notice.bean.NoticeListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author:: wangjianchi
 * @time: 2017/11/23  16:42.
 * @description:
 */

public class NoticeListActivity extends BaseActivity {
    @BindView(R.id.rv_notice)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    private List<NoticeListBean> mData = new ArrayList<>();
    private NoticeAdapter mNoticeAdapter;
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_notice_list;
    }

    @Override
    protected void initViews() {
        mTextTitle.setText("公告");
        for (int i = 0; i < 20 ; i ++){
            mData.add(new NoticeListBean());
        }
        mNoticeAdapter = new NoticeAdapter(mData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mNoticeAdapter);
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
}
