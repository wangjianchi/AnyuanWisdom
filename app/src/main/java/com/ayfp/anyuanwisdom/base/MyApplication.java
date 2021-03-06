package com.ayfp.anyuanwisdom.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.nim.NimCache;
import com.ayfp.anyuanwisdom.nim.NimSDKOptionConfig;
import com.ayfp.anyuanwisdom.nim.session.NimDemoLocationProvider;
import com.ayfp.anyuanwisdom.nim.session.SessionHelper;
import com.ayfp.anyuanwisdom.retrofit.RetrofitService;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.tencent.bugly.Bugly;


/**
 * @author:: wangjianchi
 * @time: 2017/11/23  15:07.
 * @description:
 */

public class MyApplication extends Application {
    private static Context mContext;
    public static Context getContext() {
        return mContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        Bugly.init(getApplicationContext(), "1defb5ee1e", true);

        RetrofitService.init();
        NimCache.setContext(this);
        // 初始化云信SDK
        NIMClient.init(this, getLoginInfo(), NimSDKOptionConfig.getSDKOptions(this));
        // 以下逻辑只在主进程初始化时执行
        if (NIMUtil.isMainProcess(this)) {
            initUIKit();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    private LoginInfo getLoginInfo() {
        String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();
        Log.i("MyApplication", "getLoginInfo: "+token+"  "+account);

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            NimCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }
    private void initUIKit() {
        // 初始化
        NimUIKit.init(this);

        // 设置地理位置提供者。如果需要发送地理位置消息，该参数必须提供。如果不需要，可以忽略。
        NimUIKit.setLocationProvider(new NimDemoLocationProvider());

        // IM 会话窗口的定制初始化。
        SessionHelper.init();


    }






}
