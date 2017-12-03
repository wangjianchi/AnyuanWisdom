package com.ayfp.anyuanwisdom.view.notice;

import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.retrofit.AppResultData;
import com.ayfp.anyuanwisdom.retrofit.BaseObserver;
import com.ayfp.anyuanwisdom.retrofit.RetrofitService;
import com.ayfp.anyuanwisdom.utils.CommonUtils;
import com.ayfp.anyuanwisdom.view.notice.bean.NoticeDetail;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:: wangjianchi
 * @time: 2017/11/30  15:16.
 * @description:
 */

public class NoticeDetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    private String id;

    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_notice_detail;
    }

    @Override
    protected void initViews() {
        mTextTitle.setText("公告详情");
        id = getIntent().getStringExtra("id");
        loadNoticeDetail();
    }

    private void loadNoticeDetail(){
        RetrofitService.getApi().getAfficheDetail(RetrofitService.TOKEN, Preferences.getUserName(), CommonUtils.StringToInt(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<AppResultData<NoticeDetail>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<NoticeDetail>>() {
                    @Override
                    public void loadSuccess(AppResultData<NoticeDetail> data) {

                    }
                });

//        RetrofitService.getApi().getIndexAffiche(RetrofitService.TOKEN,0,CommonUtils.StringToInt(id))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(this.<AppResultData<Object>>bindToLife())
//                .subscribe(new BaseObserver() {
//                    @Override
//                    public void loadSuccess(Object o) {
//
//                    }
//                });
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    @OnClick(R.id.iv_back) void back(){
        finish();
    }
}
