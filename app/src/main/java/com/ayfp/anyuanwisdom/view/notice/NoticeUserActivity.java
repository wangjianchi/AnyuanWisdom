package com.ayfp.anyuanwisdom.view.notice;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.retrofit.AppResultData;
import com.ayfp.anyuanwisdom.retrofit.BaseObserver;
import com.ayfp.anyuanwisdom.retrofit.RetrofitService;
import com.ayfp.anyuanwisdom.view.notice.adapter.ReadUserAdpater;
import com.ayfp.anyuanwisdom.view.notice.bean.NoticeDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:: wangjianchi
 * @time: 2017/12/15  11:03.
 * @description:
 */

public class NoticeUserActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    @BindView(R.id.rv_read)
    RecyclerView mRecyclerViewRead;
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_notice_user;
    }

    @Override
    protected void initViews() {
        mTextTitle.setText("公告详情");
        Intent intent = getIntent();
        getData(intent.getIntExtra("id",0),intent.getIntExtra("is_read",0));
    }
    private void getData(int id,int is_read){
        showProgress();
        RetrofitService.getApi().getAfficheUsers(RetrofitService.TOKEN,is_read,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<AppResultData<List<NoticeDetail.ReadUsersBean>>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<List<NoticeDetail.ReadUsersBean>>>() {

                    @Override
                    public void loadSuccess(AppResultData<List<NoticeDetail.ReadUsersBean>> data) {
                        dismissProgress();
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            loadView(data.getResult());
                        }
                    }
                });
    }
    private void loadView(List<NoticeDetail.ReadUsersBean> readUsersBeans){
        ReadUserAdpater adapter = new ReadUserAdpater(readUsersBeans);
        mRecyclerViewRead.setLayoutManager(new GridLayoutManager(this,7));
        mRecyclerViewRead.setAdapter(adapter);
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    @OnClick(R.id.iv_back) void back(){
        finish();
    }
}
