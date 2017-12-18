package com.ayfp.anyuanwisdom.view.notice;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.retrofit.AppResultData;
import com.ayfp.anyuanwisdom.retrofit.BaseObserver;
import com.ayfp.anyuanwisdom.retrofit.RetrofitService;
import com.ayfp.anyuanwisdom.view.notice.adapter.NoticeAdapter;
import com.ayfp.anyuanwisdom.view.notice.bean.NoticeListBean;
import com.ayfp.anyuanwisdom.weidgts.CustomLoadMoreView;
import com.ayfp.anyuanwisdom.weidgts.SpringView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    @BindView(R.id.springView)
    SpringView mSpringView;
    private List<NoticeListBean> mData = new ArrayList<>();
    private NoticeAdapter mNoticeAdapter;
    private int start = 0;
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
        mSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                start = 0;
                getNoticeList();
            }
        });
        mNoticeAdapter = new NoticeAdapter(mData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mNoticeAdapter);
        mNoticeAdapter.setLoadMoreView(new CustomLoadMoreView());
        mNoticeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                start = mData.size();
                getNoticeList();
            }
        },mRecyclerView);
        mNoticeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(NoticeListActivity.this,NoticeDetailActivity.class);
                intent.putExtra("id",mData.get(position).getId());
                startActivity(intent);
                mData.get(position).setStatus(1);
                mNoticeAdapter.notifyItemChanged(position);
            }
        });
        showProgress();
        getNoticeList();
    }


    private void getNoticeList(){
        RetrofitService.getApi().getAfficheByLimit(RetrofitService.TOKEN, Preferences.getUserName(),start,RetrofitService.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<AppResultData<List<NoticeListBean>>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<List<NoticeListBean>>>() {

                    @Override
                    public void loadSuccess(AppResultData<List<NoticeListBean>> listAppResultData) {
                        dismissProgress();
                        mSpringView.onFinishFreshAndLoad();
                        if (listAppResultData.getStatus() == RetrofitService.SUCCESS){
                            if (listAppResultData.getResult() != null && listAppResultData.getResult().size() > 0){
                                if (start ==  0){
                                    mData.clear();
                                }
                                mData.addAll(listAppResultData.getResult());
                                mNoticeAdapter.notifyDataSetChanged();
                            }else {
                                mNoticeAdapter.loadMoreEnd();
                            }
                        }
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
