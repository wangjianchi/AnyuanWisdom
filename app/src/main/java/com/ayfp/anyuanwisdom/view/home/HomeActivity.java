package com.ayfp.anyuanwisdom.view.home;

import android.content.Intent;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.view.contacts.ContactsActivity;
import com.ayfp.anyuanwisdom.view.live.LiveActivity;
import com.ayfp.anyuanwisdom.view.notice.NoticeListActivity;
import com.ayfp.anyuanwisdom.view.personal.MineActivity;

import butterknife.OnClick;

/**
 * @author:: wangjianchi
 * @time: 2017/11/23  15:17.
 * @description:
 */

public class HomeActivity extends BaseActivity {
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.iv_personal) void mine(){
        Intent intent = new Intent(this, MineActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.iv_contacts) void contacts(){
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.iv_live) void live(){
        Intent intent = new Intent(this, LiveActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.iv_notice) void notice(){
        Intent intent = new Intent(this, NoticeListActivity.class);
        startActivity(intent);
    }
}
