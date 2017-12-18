package com.ayfp.anyuanwisdom.view.live;

import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author:: wangjianchi
 * @time: 2017/11/23  16:41.
 * @description:
 */

public class LiveActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_live;
    }

    @Override
    protected void initViews() {
        mTextTitle.setText("直播");
      //  startActivity(new Intent(this,CallLiveActivity.class));
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    @OnClick(R.id.iv_back) void back(){
        finish();
    }
}
