package com.ayfp.anyuanwisdom.base;

import android.app.Application;
import android.content.Context;

/**
 * @author:: wangjianchi
 * @time: 2017/11/23  15:07.
 * @description:
 */

public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
