package com.ayfp.anyuanwisdom.view.personal;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.bean.UserBean;
import com.ayfp.anyuanwisdom.config.cache.AppCache;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.retrofit.AppResultData;
import com.ayfp.anyuanwisdom.retrofit.BaseObserver;
import com.ayfp.anyuanwisdom.retrofit.RetrofitService;
import com.ayfp.anyuanwisdom.utils.GlideUtils;
import com.ayfp.anyuanwisdom.view.personal.adapter.PersonSettingAdapter;
import com.ayfp.anyuanwisdom.view.personal.bean.PersonSettingBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nim.uikit.common.ui.imageview.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:: wangjianchi
 * @time: 2017/11/23  15:42.
 * @description:
 */

public class MineActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.iv_head)
    CircleImageView mImageHead;
    @BindView(R.id.tv_username)
    TextView mTextName;
    @BindView(R.id.tv_organization)
    TextView mTextOrganization;
    private String mSettingNames[] = {"个人资料","单位信息","修改密码","意见反馈","帮助"};
    private int mResourceId[] = {R.mipmap.icon_person_data,R.mipmap.icon_person_company,R.mipmap.icon_person_password,R.mipmap.icon_person_feedback,R.mipmap.icon_person_help};
    private PersonSettingAdapter mAdapter;
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_mine;
    }

    @Override
    protected void initViews() {
        mTextTitle.setText("我的");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initSettingData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserMsg(AppCache.getInstance().getUserBean());
    }

    private void initSettingData(){
        final List<PersonSettingBean> list = new ArrayList<>();
        for (int i = 0;i < mSettingNames.length;i++){
            PersonSettingBean personSettingBean = new PersonSettingBean();
            personSettingBean.setName(mSettingNames[i]);
            personSettingBean.setDrawable(getResources().getDrawable(mResourceId[i]));
            if (i == 2){
                personSettingBean.setDiliver(true);
            }
            list.add(personSettingBean);
        }
        mAdapter = new PersonSettingAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (list.get(position).getName().equals("修改密码")){
                    Intent intent = new Intent(MineActivity.this,EditPasswordActivity.class);
                    startActivity(intent);
                }else if (list.get(position).getName().equals("个人资料")){
                    Intent intent = new Intent(MineActivity.this,EditPersonalActivity.class);
                    startActivity(intent);
                }
            }
        });
        if (AppCache.getInstance().getUserBean() == null){
            getUserMsg();
        }else {
            updateUserMsg(AppCache.getInstance().getUserBean());
        }
    }

    private void getUserMsg(){
        RetrofitService.getApi().getUserMsg(RetrofitService.TOKEN, Preferences.getUserName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<AppResultData<UserBean>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<UserBean>>() {
                    @Override
                    public void loadSuccess(AppResultData<UserBean> data) {
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            UserBean userBean = data.getResult();
                            AppCache.getInstance().setUserBean(userBean);
                            updateUserMsg(userBean);
                        }
                    }
                });
    }

    private void updateUserMsg(UserBean userBean){
        if (userBean != null){
            GlideUtils.loadImageViewErr(userBean.getPortrait(),mImageHead,R.mipmap.image_head);
            mTextName.setText(userBean.getReal_name());
            mTextOrganization.setText(userBean.getOrganization());
        }
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    @OnClick(R.id.iv_back) void back(){
        finish();
    }
    @OnClick(R.id.layout_head) void personal(){
        Intent intent = new Intent(this,EditPersonalActivity.class);
        startActivity(intent);
    }
}
