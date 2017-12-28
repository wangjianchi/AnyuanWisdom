package com.ayfp.anyuanwisdom.view;

import android.content.Context;
import android.content.Intent;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.utils.GlideUtils;
import com.ayfp.anyuanwisdom.weidgts.PinchImageView;

import butterknife.BindView;

/**
 * @author:: wangjianchi
 * @time: 2017/12/28  15:48.
 * @description:
 */

public class ImageBrowserActivity extends BaseActivity{
    @BindView(R.id.iv_image)
    PinchImageView mPinchImageView;
    public static void  start(Context context,String url){
        Intent intent = new Intent(context,ImageBrowserActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_image_browser;
    }

    @Override
    protected void initViews() {
        String url = getIntent().getStringExtra("url");
        GlideUtils.loadImageView(url,mPinchImageView);
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
}
