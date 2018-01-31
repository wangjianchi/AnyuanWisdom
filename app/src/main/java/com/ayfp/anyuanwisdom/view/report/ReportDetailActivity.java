package com.ayfp.anyuanwisdom.view.report;

import android.support.v7.widget.GridLayoutManager;
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
import com.ayfp.anyuanwisdom.view.ImageBrowserActivity;
import com.ayfp.anyuanwisdom.view.report.adapter.ReportImageAdapter;
import com.ayfp.anyuanwisdom.view.report.bean.EventBean;
import com.ayfp.anyuanwisdom.view.report.bean.ReportImageBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:: wangjianchi
 * @time: 2018/1/31  15:54.
 * @description:
 */

public class ReportDetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    @BindView(R.id.rv_report_pic)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_event_title)
    TextView tv_event_title;
    @BindView(R.id.tv_category)
    TextView tv_category;
    @BindView(R.id.tv_degree)
    TextView tv_degree;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_event_content)
    TextView tv_event_content;
    private int eventId;
    private List<ReportImageBean> mData = new ArrayList<>();
    private ReportImageAdapter mReportImageAdapter;
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_report_detail;
    }

    @Override
    protected void initViews() {
        mTextTitle.setText("事件上报");
        eventId = getIntent().getIntExtra("id",0);
        getEventDetail(eventId);
        mReportImageAdapter = new ReportImageAdapter(mData);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setAdapter(mReportImageAdapter);
        mReportImageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ImageBrowserActivity.start(ReportDetailActivity.this,mData.get(position).getImageFile());
            }
        });
    }

    private void getEventDetail(final int eventId){
        showProgress();
        RetrofitService.getApi().getEventReportById(RetrofitService.TOKEN, Preferences.getUserName(),eventId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<AppResultData<EventBean>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<EventBean>>() {

                    @Override
                    public void loadSuccess(AppResultData<EventBean> data) {
                        dismissProgress();
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            EventBean eventBean = data.getResult();
                            tv_event_title.setText("主题："+eventBean.getTitle());
                            tv_category.setText("事件分类："+eventBean.getEvent_category());
                            tv_degree.setText(eventBean.getEvent_degree());
                            tv_status.setText(eventBean.getEvent_status());
                            tv_event_content.setText(eventBean.getContent());
                        }
                    }
                });
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
