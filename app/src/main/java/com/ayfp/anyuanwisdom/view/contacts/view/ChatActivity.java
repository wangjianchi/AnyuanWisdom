package com.ayfp.anyuanwisdom.view.contacts.view;

import android.os.Bundle;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.netease.nim.uikit.business.session.constant.Extras;
import com.netease.nim.uikit.business.session.fragment.MessageFragment;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

/**
 * Created by 建池 on 2017/12/7.
 */

public class ChatActivity extends BaseActivity {
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initViews() {
        String sessionId = getIntent().getStringExtra(Extras.EXTRA_ACCOUNT);
        MessageFragment fragment = new MessageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Extras.EXTRA_ACCOUNT,sessionId);
        bundle.putSerializable(Extras.EXTRA_TYPE, SessionTypeEnum.P2P);
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
}
