package com.ayfp.anyuanwisdom.view.contacts.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.utils.GlideUtils;
import com.ayfp.anyuanwisdom.utils.ToastUtils;
import com.ayfp.anyuanwisdom.view.contacts.bean.Person;
import com.netease.nim.uikit.common.ui.imageview.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author:: wangjianchi
 * @time: 2017/12/13  10:41.
 * @description:
 */

public class UserDetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    @BindView(R.id.iv_head)
    CircleImageView mImageHead;
    @BindView(R.id.tv_username)
    TextView mTextName;
    @BindView(R.id.tv_organization)
    TextView mTextOrganization;
    @BindView(R.id.tv_department)
    TextView mTextDepart;
    @BindView(R.id.tv_position)
    TextView mTextPosition;
    @BindView(R.id.tv_tel)
    TextView mTextTel;
    private Person mPerson;
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_user_detail;
    }

    @Override
    protected void initViews() {
        mTextTitle.setText("详情资料");
        mPerson = (Person) getIntent().getSerializableExtra("userbean");
        mTextName.setText(mPerson.getReal_name());
        mTextOrganization.setText("单位："+mPerson.getOrganization());
        mTextDepart.setText("部门："+mPerson.getDepartment());
        mTextPosition.setText("职务："+mPerson.getJob_position());
        mTextTel.setText("电话："+mPerson.getPhone_tel());
        GlideUtils.loadImageViewErr(mPerson.getPortrait(),mImageHead,R.mipmap.image_head);
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    @OnClick(R.id.iv_back) void back(){
        finish();
    }
    @OnClick(R.id.iv_commit) void commit(){
        ChatActivity.start(this,mPerson.getAccount(),mPerson.getReal_name());
    }
    @OnClick(R.id.iv_tel) void  openTel(){
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mPerson.getPhone_tel()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            e.printStackTrace();
            ToastUtils.showToast("没有找到电话应用");
        }
    }
}
