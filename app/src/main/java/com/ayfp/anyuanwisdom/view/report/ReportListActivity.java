package com.ayfp.anyuanwisdom.view.report;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.ayfp.anyuanwisdom.utils.CommonUtils;
import com.ayfp.anyuanwisdom.utils.ToastUtils;
import com.ayfp.anyuanwisdom.view.report.adapter.EventListAdapter;
import com.ayfp.anyuanwisdom.view.report.bean.EventListBean;
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
 * @time: 2018/1/31  13:37.
 * @description:
 */

public class ReportListActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    @BindView(R.id.springView)
    SpringView mSpringView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private int start = 0;
    private List<EventListBean> mData = new ArrayList<>();
    private EventListAdapter mAdapter;

    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_report_list;
    }

    @Override
    protected void initViews() {
        mAdapter = new EventListAdapter(mData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                start = 0;
                getEventList();
            }
        });
        showProgress();
        getEventList();
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                start = mData.size();
                getEventList();
            }
        }, mRecyclerView);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                switch (view.getId()) {
                    case R.id.iv_delete:
                        final AlertDialog.Builder builder = new AlertDialog.Builder(ReportListActivity.this);
                        builder.setTitle("确认删除事件？")
                                .setCancelable(true)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                            deleteItem(mData.get(position).getId());
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                        builder.show();
                        break;
                    case R.id.layout_item:
                        Intent intent = new Intent(ReportListActivity.this,ReportDetailActivity.class);
                        intent.putExtra("id", CommonUtils.StringToInt(mData.get(position).getId()));
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void getEventList() {
        RetrofitService.getApi().getEventReportByLimit(RetrofitService.TOKEN, Preferences.getUserName(), start, RetrofitService.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<AppResultData<List<EventListBean>>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<List<EventListBean>>>() {
                    @Override
                    public void loadSuccess(AppResultData<List<EventListBean>> listAppResultData) {
                        dismissProgress();
                        mSpringView.onFinishFreshAndLoad();
                        if (listAppResultData.getStatus() == RetrofitService.SUCCESS) {
                            if (listAppResultData.getResult() != null && listAppResultData.getResult().size() > 0) {
                                if (start == 0) {
                                    mData.clear();
                                }
                                mData.addAll(listAppResultData.getResult());
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter.loadMoreEnd();
                            }
                        }
                    }
                });
    }

    private void deleteItem(String reportId){
        showProgress();
        RetrofitService.getApi().deleteEventReport(RetrofitService.TOKEN, Preferences.getUserName(), CommonUtils.StringToInt(reportId))
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.<AppResultData<Object>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<Object>>() {

                    @Override
                    public void loadSuccess(AppResultData<Object> data) {
                        dismissProgress();
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            ToastUtils.showToast("删除成功");
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
