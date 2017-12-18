package com.ayfp.anyuanwisdom.view.notice;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.retrofit.AppResultData;
import com.ayfp.anyuanwisdom.retrofit.BaseObserver;
import com.ayfp.anyuanwisdom.retrofit.RetrofitService;
import com.ayfp.anyuanwisdom.utils.CommonUtils;
import com.ayfp.anyuanwisdom.view.notice.adapter.ReadUserAdpater;
import com.ayfp.anyuanwisdom.view.notice.bean.NoticeDetail;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

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
    @BindView(R.id.tv_notice_title)
    TextView mTextNoticeTitle;
    @BindView(R.id.tv_date_author)
    TextView mTextDataAuthor;
    @BindView(R.id.wv_content)
    WebView mWebView;
    @BindView(R.id.rv_read)
    RecyclerView mRecyclerViewRead;
    @BindView(R.id.rv_unread)
    RecyclerView mRecyclerViewUnread;
    private String id;
    private NoticeDetail mNoticeDetail;

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
        showProgress();
        RetrofitService.getApi().getAfficheDetail(RetrofitService.TOKEN, Preferences.getUserName(), CommonUtils.StringToInt(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<AppResultData<NoticeDetail>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<NoticeDetail>>() {
                    @Override
                    public void loadSuccess(AppResultData<NoticeDetail> data) {
                        dismissProgress();
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            mNoticeDetail = data.getResult();
                            loadAfficheView(mNoticeDetail.getAffiche());
                            initReadUser(mNoticeDetail.getRead_users(),mRecyclerViewRead,1);
//                            initReadUser(mNoticeDetail.getUnread_users(),mRecyclerViewUnread,0);
                        }
                    }
                });
    }

    private void loadAfficheView(NoticeDetail.AfficheBean afficheBean){
        mTextNoticeTitle.setText(afficheBean.getTitle());
        mTextDataAuthor.setText(afficheBean.getAuthor()+" "+afficheBean.getAdd_time());
        mWebView.loadDataWithBaseURL(null,afficheBean.getHtml_content(),"text/html", "utf-8",null);
    }
    private void initReadUser(List<NoticeDetail.ReadUsersBean> readUsersBeans, RecyclerView recyclerView, final int is_read){
        if (readUsersBeans.size() > 10){
            readUsersBeans = readUsersBeans.subList(0,10);
            NoticeDetail.ReadUsersBean usersBean = new NoticeDetail.ReadUsersBean();
            usersBean.setType(1);
            readUsersBeans.add(usersBean);
        }

        ReadUserAdpater adapter = new ReadUserAdpater(readUsersBeans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(NoticeDetailActivity.this,NoticeUserActivity.class);
                intent.putExtra("id",CommonUtils.StringToInt(id));
                intent.putExtra("is_read",is_read);
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
