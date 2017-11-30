package com.ayfp.anyuanwisdom.view.notice;

import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author:: wangjianchi
 * @time: 2017/11/30  15:16.
 * @description:
 */

public class NoticeDetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTextTitle;

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

    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    @OnClick(R.id.iv_back) void back(){
        finish();
    }
}
