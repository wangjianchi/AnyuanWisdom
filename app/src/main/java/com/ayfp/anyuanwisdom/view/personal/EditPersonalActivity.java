package com.ayfp.anyuanwisdom.view.personal;

import android.content.Intent;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author:: wangjianchi
 * @time: 2017/12/1  9:44.
 * @description:
 */

public class EditPersonalActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_edit_personal;
    }

    @Override
    protected void initViews() {
        mTextTitle.setText("个人资料");
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    @OnClick(R.id.iv_back) void back(){
        finish();
    }

}
