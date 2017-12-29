package com.ayfp.anyuanwisdom.view.live;

import android.content.Intent;
import android.view.WindowManager;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.nim.avchat.AVChatSoundPlayer;
import com.ayfp.anyuanwisdom.retrofit.AppResultData;
import com.ayfp.anyuanwisdom.retrofit.BaseObserver;
import com.ayfp.anyuanwisdom.retrofit.RetrofitService;
import com.ayfp.anyuanwisdom.view.live.bean.LivePushUrlBean;

import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:: wangjianchi
 * @time: 2017/12/5  13:40.
 * @description:
 */

public class CallLiveActivity extends BaseActivity {
    private String url="";
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_call_live;
    }

    @Override
    protected void initViews() {
        dismissKeyguard();
        AVChatSoundPlayer.instance().play(AVChatSoundPlayer.RingerTypeEnum.RING);
        RetrofitService.getApi().getLivePushUrl(RetrofitService.TOKEN, Preferences.getUserName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<AppResultData<LivePushUrlBean>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<LivePushUrlBean>>() {

                    @Override
                    public void loadSuccess(AppResultData<LivePushUrlBean> data) {
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            url = data.getResult().getPush_url();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AVChatSoundPlayer.instance().stop();
    }

    // 设置窗口flag，亮屏并且解锁/覆盖在锁屏界面上
    private void dismissKeyguard() {
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        );
    }
    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    @OnClick(R.id.tv_accept_call) void call(){
        Intent intent = new Intent(this,LiveStreamingActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);
        AVChatSoundPlayer.instance().stop();
        finish();
    }
}
