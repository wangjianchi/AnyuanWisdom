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
 * @time: 2017/11/23  15:42.
 * @description:
 */

public class MineActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTextTitle;
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
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    @OnClick(R.id.iv_back) void back(){
        finish();
    }
    @OnClick(R.id.roundImage) void personal(){
        Intent intent = new Intent(this,EditPersonalActivity.class);
        startActivity(intent);
    }
}
