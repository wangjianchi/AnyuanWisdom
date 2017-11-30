package com.ayfp.anyuanwisdom.view.notice;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.view.notice.adapter.NoticeAdapter;
import com.ayfp.anyuanwisdom.view.notice.bean.NoticeListBean;
import com.ayfp.anyuanwisdom.weidgts.CustomLoadMoreView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
        mNoticeAdapter.setLoadMoreView(new CustomLoadMoreView());
        mNoticeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mNoticeAdapter.loadMoreEnd();
            }
        },mRecyclerView);
        mNoticeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(NoticeListActivity.this,NoticeDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.iv_back) void back(){
        finish();
    }
}
