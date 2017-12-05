package com.ayfp.anyuanwisdom.view.live;

import android.content.Intent;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;

import butterknife.OnClick;

/**
 * @author:: wangjianchi
 * @time: 2017/12/5  13:40.
 * @description:
 */

public class CallLiveActivity extends BaseActivity {
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_call_live;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    @OnClick(R.id.tv_accept_call) void call(){
        startActivity(new Intent(this,LiveStreamingActivity.class));
    }
}
